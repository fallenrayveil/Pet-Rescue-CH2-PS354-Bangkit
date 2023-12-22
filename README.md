<h1 align="center">Pet Rescue - Machine Learning Documentation</h1>

<p align="center">
  <img src="https://cdn.discordapp.com/attachments/1166761620358299658/1187411260929740890/PetRescue_App.png" style="width:100%;">
</p>

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

## Model Architecture
<p align="left">
InceptionV3 stands as an advanced deep learning model crafted explicitly for image classification purposes. Its design includes a complex structure composed of convolutional layers, pooling layers, and fully connected layers. This intricate architecture allows for the extraction of hierarchical features at different levels of complexity, thereby enabling precise identification and classification of both cats and dogs. <br>
Moreover, alongside its core architecture, InceptionV3 integrates auxiliary classifiers within intermediate layers. These additional classifiers play a role in aiding gradient propagation during the model's training phase, thereby amplifying the overall efficiency and acceleration of the model's convergence.
</p>

## Training
Train the InceptionV3 model with the labeled images from the training set using well-known machine learning frameworks or libraries like TensorFlow or Keras. These frameworks offer pre-existing implementations of InceptionV3. Adjust hyperparameters and training settings through experimentation and based on the model's performance for fine-tuning purposes.
### Pre-built implementations of InceptionV3
<code># Download the pre-trained weights. No top means it excludes the fully connected layer it uses for classification.
!wget --no-check-certificate \
    https://storage.googleapis.com/mledu-datasets/inception_v3_weights_tf_dim_ordering_tf_kernels_notop.h5 \
    -O /tmp/inception_v3_weights_tf_dim_ordering_tf_kernels_notop.h5</code>

### Fine-tune hyperparameters and Training configurations
| Type    | Value    |
|------------|------------|
| Learning Rate | <code>0.0001</code> | 
| Optimizer | <code>Adam</code> | 
| Batch Size | <code>32</code> | 
| Number of Training Epochs | <code>50</code> | 
| Input Shape | <code>(150,150,3)</code> | 
| Data Augmentation Parameters | <code>rescale=1./255,rotation_range=40,width_shift_range=0.2,</code><br><code>height_shift_range=0.2,shear_range=0.2,zoom_range=0.2,</code><br><code>fill_mode='nearest',horizontal_flip=True</code> | 
| Regularization Techniques |  <code>layers.Flatten()(last_output)</code><br><code>layers.Dense(512, activation='relu')(x)</code><br><code>layers.Dropout(0.5)(x)</code><br><code>layers.Dense (1, activation='sigmoid')(x)</code><br> | 

## Evaluation and Visualitation
Once the model training is complete, evaluate its performance using the test set. Measure accuracy and other relevant evaluation metrics to assess the model's classification capability.

### Model Accuracy & Lose
<code>- loss: 0.1198 - accuracy: 0.9492 - val_loss: 0.0799 - val_accuracy: 0.9698</code>
<p align="left">
  <img src="https://github.com/fallenrayveil/Pet-Rescue-CH2-PS354-Bangkit/blob/f6d0a429ff1559f020112a05585865c737c5cc14/Result/accuration%20and%20loss.png" alt="Deskripsi Gambar" style="width:80%; border: 1px solid black;">
</p>


