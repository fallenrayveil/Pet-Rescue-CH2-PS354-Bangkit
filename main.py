# import library
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

import io
import tensorflow
from tensorflow import keras
import numpy as np
from PIL import Image
from flask import Flask, request, jsonify

# Load inceptionv3.h5 pke keras
model = keras.models.load_model("inceptionv3.h5")
# Label yang ada di dalam inceptionv3.h5
label = ["Cat", "Dog"]

app = Flask(__name__)

# Function yang berfungsi untuk melakukan prediksi pd gambar yang di input
def predict_label(img):
    try:
        img = img.resize((150, 150), Image.NEAREST)  # Resize to (150, 150)
        img_array = np.array(img)
        img_array = img_array.reshape(1, 150, 150, 3)  # Reshape to (1, 150, 150, 3)
        pred = model.predict(img_array)
        pred_label = "Dog" if pred[0][0] == 1 else "Cat"
        return pred_label
    except ValueError as e:
        return {"error": str(e)}

@app.route("/predict", methods=["GET", "POST"])
def index():
    file = request.files.get('file')
    if file is None or file.filename == "":
        return jsonify({"error": "no file"})
    
    image_bytes = file.read()
    img = Image.open(io.BytesIO(image_bytes))
    img = img.resize((150, 150), Image.NEAREST)
    pred_img = predict_label(img)
    return pred_img

if __name__ == "__main__":
    app.run(debug=True)