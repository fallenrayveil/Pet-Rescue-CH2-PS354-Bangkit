FROM python:3.9-slim

# Set the working directory inside the Docker image
WORKDIR /app

# Copy the requirements.txt file to the working directory
COPY requirements.txt ./requirements.txt

# Install the required Python packages specified in requirements.txt
RUN pip install -r requirements.txt

# Copy the pre-trained model file from your local machine to the Docker image
COPY model_quantized_float16.tflite ./post_training_quantization/model_quantized_float16.tflite

# Copy the entire content of the current directory to the working directory inside the Docker image
COPY . .

# Specify the command to run when the Docker container starts
CMD ["python", "app.py"]