<h1 align="center">Pet Rescue - Machine Learning Documentation</h1>

### Function Dependencies

| Library    | Version    |
|------------|------------|
| Tensorflow | <code>^2.15.0</code> | 
| Keras | <code>^2.15.0</code> | 
| Matplotlib | <code>^3.8.2</code> | 
| NumPy | <code>^1.26.2</code> | 
| Pandas | <code>^2.1.4</code> | 
| Scikit-learn | <code>^1.4.0</code> | 
| Seaborn | <code>^0.13.0</code> | 


## Transfer Learning InceptionV3
<p align="left">
InceptionV3 is a deep learning model utilized for image classification, including cats and dogs classification. It has been pre-trained using a vast dataset and possesses the ability to extract relevant features from both cat and dog images. The model's architecture comprises convolutional layers, pooling layers, and fully connected layers. Trained extensively with labeled images of cats and dogs, InceptionV3 proficiently classifies new images by assigning probabilities to differentiate between cats and dogs. With its capabilities, InceptionV3 accurately distinguishes between images of cats and dogs based on their visual characteristics.
</p>

## Dataset
<p align="left">
This dataset consists of a collection of cat and dog images obtained from Kaggle, which we subsequently divided into two main parts: the training set and the test set. The training set plays a crucial role in training the InceptionV3 model by providing the training data, while the test set is utilized to measure how well this model performs in classifying between cat and dog images and to evaluate its overall performance.
</p>

| Dataset    | Name    | Total    |
|------------|------------|------------|
| Training Set | Cats | 4.000 | 
| Training Set | Dogs | 4.000 |  
| Test Set | Cats | 1.000 | 
| Test Set | Dogs | 1.000 |  
