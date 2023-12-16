from flask import Flask, request, jsonify
from PIL import Image
import tensorflow as tf
import numpy as np
import io

app = Flask(__name__)

# Load the pre-trained TensorFlow Lite model
model = tf.lite.Interpreter(model_path="post_training_quantization/model_quantized_float16.tflite")
model.allocate_tensors()

@app.route('/predict', methods=['POST'])
def predict():
    """
    Endpoint for making predictions.
    Expects a POST request with an image file in the 'file' field.
    Returns a JSON response with the predicted result.
    """
    # Read the image file from the request
    data = request.files['file'].read()
    
    # Open and resize the image using Pillow (PIL)
    
    image = Image.open(io.BytesIO(data)).resize((128, 128))
    
    # Convert the image to a NumPy array
    image = np.array(image)  # RGB
    
    # Convert RGB to BGR (required by the model)
    image = image[:, :, ::-1]
    
    # Normalize the image by dividing by 255.0
    image = image / 255.0
    
    # Get input and output details of the TensorFlow Lite model
    input_details = model.get_input_details()
    output_details = model.get_output_details()
    
    # Expand dimensions of the image to match the input shape of the model
    image = np.expand_dims(image, axis=0).astype(input_details[0]['dtype'])
    
    # Set the input tensor of the model
    model.set_tensor(input_details[0]['index'], image)
    
    # Run the model inference
    model.invoke()
    
    # Get the output tensor of the model
    output_data = model.get_tensor(output_details[0]['index'])
    
    # Convert the output from a NumPy array to a Python list
    output_data_list = output_data.tolist()
    
    # Return the predicted result as a JSON response
    return jsonify({"result": output_data_list})

if __name__ == '__main__':
    # Run the Flask app on the specified host and port
    app.run(host='0.0.0.0', port=8080)