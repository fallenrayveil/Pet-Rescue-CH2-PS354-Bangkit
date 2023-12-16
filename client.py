import requests
import numpy as np
import matplotlib.pyplot as plt
import json
import time

# Use the URL of your deployed application
url = 'put_your_http_url_which_youll_get_after_successfull_deployment/predict'

# Open your image file in binary mode
with open('test_image.jpg', 'rb') as img_file:
    file_dict = {'file': img_file}
    
    start_time = time.time()  # Start measuring the time
    
    # Make a POST request to the server
    response = requests.post(url, files=file_dict)
    
    end_time = time.time()  # Stop measuring the time
    
# The response will contain the segmented image data and shape
response_dict = json.loads(response.text)

# Convert result list to numpy array. Adjust dtype according to your model's output.
segmented_image_array = np.array(response_dict['result'], dtype=np.float16)

elapsed_time = end_time - start_time
print(f"Request completed in {elapsed_time:.2f} seconds")

# Plot the image using matplotlib
plt.imshow(segmented_image_array.squeeze(), cmap='gray')  # use squeeze to remove single-dimensional entries from the shape of an array.
plt.show()