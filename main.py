# import library
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

import io
import tensorflow as tf
from tensorflow import keras
import numpy as np
from PIL import Image
from flask import Flask, request, jsonify
from google.cloud import storage
import mysql.connector
from dotenv import load_dotenv

# Load environment variables from .env file
load_dotenv()


# Establish a connection to the database
db = mysql.connector.connect(
    host=os.getenv('DB_HOST'),
    user=os.getenv('DB_USER'),
    password=os.getenv('DB_PASSWORD'),
    database=os.getenv('DB_DATABASE')
)

cursor = db.cursor()

# Load inceptionv3.h5 pke keras
model = tf.keras.models.load_model('inceptionv3.h5')
# Label yang ada di dalam inceptionv3.h5
label = ['Cat', 'Dog']

# Set up Google Cloud Storage
os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = "config/pet-rescue-407209-74395c7dca01.json"
storage_client = storage.Client()

app = Flask(__name__)

# Function yang berfungsi untuk melakukan prediksi pd gambar yang di input
def predict_label(img):
    try:
        img = img.resize((150, 150), Image.NEAREST)  # Resize to (150, 150)
        img_array = np.array(img)
        img_array = img_array.reshape(1, 150, 150, 3)  # Reshape to (1, 150, 150, 3)
        pred = model.predict(img_array)
        # pred_label = "Cat" if pred[0][0] == 0.9824213981628418 else "Dog" if pred[0][0] == 1 else "Cat"
        pred_label = "Cat" if pred[0][0] < 1 else "Dog"
        return pred_label
    except ValueError as e:
        return {"error": str(e)}

@app.route("/predict", methods=["GET", "POST"])
def index():
    file = request.files.get('image')
    if file is None or file.filename == "":
        return jsonify({"error": "no image"})
    
    image_bytes = file.read()
    img = Image.open(io.BytesIO(image_bytes))
    img = img.resize((150, 150), Image.NEAREST)
    pred_img = predict_label(img)

    # Upload the file to Google Cloud Storage
    bucket = storage_client.get_bucket('pet-rescue-407209.appspot.com')
    blob = bucket.blob(file.filename)
    blob.upload_from_string(image_bytes, content_type=file.content_type)

    # Make the blob publicly viewable
    blob.make_public()

    # Get the public URL of the blob
    image_url = blob.public_url

    print(image_url)
    # Insert the URL into your SQL database
    query = """
    INSERT INTO pets 
    (
        pet_name,
        type,
        gender,
        image_url,
        province,
        regency,
        found_area,
        email,
        phone_number)
    VALUES (
        %s,
        %s,
        %s, 
        %s,
        %s,
        %s,
        %s,
        %s,
        %s)
    """
    cursor.execute(query, ('', pred_img, 'Unknown', image_url, '', '', '','',''))
    
    # Get the ID of the last inserted row
    pet_id = cursor.lastrowid

    # Commit your changes
    db.commit()

    return {"pred_img": pred_img, "pet_id": pet_id}

if __name__ == "__main__":
    app.run(debug=True)