<h1 align="center">Pet Rescue - Rest API Documentation</h1>

<p align="center">
  <img src="https://cdn.discordapp.com/attachments/1166761620358299658/1187411260929740890/PetRescue_App.png" style="width:100%;">
</p>

## Introduction

This documentation provides details about the Pet Rescue REST API endpoints and their corresponding request and response structures for the code.

The code is a Node.js with Express.js module that exposes many endpoints with specific routes, while for the model predict we use python and dockerfile for endpoint.

## Base URL

The base URL for almost all API endpoints is: `https://pet-rescue-407209.et.r.appspot.com` <br>
And the base URL for model_predict API endpoints is: `https://pet-rescue-y3vz2akfrq-et.a.run.app`

---

## Register

```
POST /register
```

This is endpoint to regist.

#### Request

- <b> Body </b>

  - name as string
  - email as string, must be unique
  - password as string, must be at least 8 characters


#### Response

- <b> Success Response </b>

  - Status Code: 201 Created

    ```json
    {
          "error": "false",
          "message": "User Created"
    }
    ```

- <b> Error Responses </b>

  - <b> Status Code: 400 Bad Request </b>

      ```json
      {
          "error": "true",
          "message": "invalid email format"
                     "Password must be at least 8 characters long"
                     "email is registered"
      }
      ```

  - <b> Status Code: 500 Internal Server Error </b>

      ```json
      {
          "error": "true",
          "message": "there is an error"
      }
      ```


## Login

```
POST /login
```

This is endpoint to login.

#### Request

- <b> Body </b>

  - email as string
  - password as string


#### Response

- <b> Success Response </b>

  - Status Code: 200 OK

    ```json
    {
          "error": false,
          "message": "success",
          "loginResult": {
          "userId": 1,
          "name": "Vyn",
          "token": "eyJhbGciOiJIU...."
    }
    ```

- <b> Error Responses </b>

  - <b> Status Code: 401 Unauthorized </b>

      ```json
      {
          "error": "true",
          "message": "Email or password is incorrect"
      }
      ```

  - <b> Status Code: 500 Internal Server Error </b>

      ```json
      {
          "error": "true",
          "message": "there is an error"
      }
      ```


## Profile

```
GET /profile
```

This is endpoint to profile.

#### Request

- <b> Body </b>

  - Name as string
  - Email as string
  - Bearer Token 

#### Response

- <b> Success Response </b>

  - Status Code: 200 OK

    ```json
    {
          "name": "andika",
          "email": "andika@gmail.com"
    }
    ```

- <b> Error Responses </b>

  - <b> Status Code: 404 Not Found </b>

      ```json
      {
          "error": "true",
          "message": "User not found"
      }
      ```

  - <b> Status Code: 500 Internal Server Error </b>

      ```json
      {
          "error": "true",
          "message": "there is an error"
      }
      ```


## Predict

```
POST /predict
```

This is endpoint to predict.

#### Request

- <b> Body </b>

  - image as file

#### Response

- <b> Success Response </b>

  - Status Code: 200 OK

    ```json
    {
          "pet_id": "1"
          "pred_img": "Cat/Dog"
    }
    ```

- <b> Error Responses </b>

  - <b> Status Code: 400 Bad Request </b>

      ```py
      {
          "error":"no image"
      }
      ```

  - <b> Status Code: 500 Internal Server Error </b>

      ```html
      {
          <!doctypo html>
          <html lang=en>
          <title>500 Internal Server Error</title>
          <h1>Internal Server Error</h1>
      }
      ```


## Lost

```
GET /lost
```

This is endpoint to show lost pet.

#### Request

- <b> Body </b>

  - No Request

#### Response

- <b> Success Response </b>

  - Status Code: 200 OK

    ```json
    {
          "pet_id": 1,
          "user_id": 1,
          "pet_name": "Poppy",
          "type": "Dog",
          "gender": "Female", ["Male / Female / Unknown"]
          "date_lost_found": "2023-01-01T00:00:00.000Z",
          "reward": "100.00",
          "image_url": "https://storage.googleapis.com/path/to/images.jpg",
          "province": "Jawa Timur",
          "regency": "Surabaya",
          "found_area": "Rungkut",
          "email": "meryline@gmail.com",
          "phone_number": "01237465874",
          "detail": "lost" ["lost / found"]
          "status": "0"
    }
    ```

- <b> Error Responses </b>

   - <b> Status Code: 500 Internal Server Error </b>

      ```json
      {
          "error": "true",
          "message": "there is an error"
      }
      ```


## Found

```
GET /found
```

This is endpoint to show found pet.

#### Request

- <b> Body </b>

  - No Request

#### Response

- <b> Success Response </b>

  - Status Code: 200 OK

    ```json
    {
          "pet_id": 2,
          "user_id": 2,
          "pet_name": "Bibo",
          "type": "Cat",
          "gender": "Male", ["Male / Female / Unknown"]
          "date_lost_found": "2023-01-01T00:00:00.000Z",
          "reward": "150.00",
          "image_url": "https://storage.googleapis.com/path/to/images.jpg",
          "province": "Jawa Timur",
          "regency": "Malang",
          "found_area": "Lowokwaru",
          "email": "vincent@gmail.com",
          "phone_number": "01422735874",
          "detail": "found" ["lost / found"]
          "status": "1"
    }
    ```

- <b> Error Responses </b>

   - <b> Status Code: 500 Internal Server Error </b>

      ```json
      {
          "error": "true",
          "message": "there is an error"
      }
      ```


## Search by Name

```
GET /search/:name
(ex: /search/Pet Name)
```

This is endpoint to search pet by name.

#### Request

- <b> Body </b>

  - No Request

#### Response

- <b> Success Response </b>

  - Status Code: 200 OK

    ```json
    {
          "pet_id": 17,
          "user_id": 9,
          "pet_name": "Feline",
          "type": "Cat",
          "gender": "Female", ["Male / Female / Unknown"]
          "date_lost_found": "2023-01-01T00:00:00.000Z",
          "reward": "175.00",
          "image_url": "https://storage.googleapis.com/path/to/images.jpg",
          "province": "Jawa Timur",
          "regency": "Surabaya",
          "found_area": "Wonokromo",
          "email": "elaine@gmail.com",
          "phone_number": "01927465874",
          "detail": "lost" ["lost / found"]
          "status": "0"
    }
    ```

- <b> Error Responses </b>

  - <b> Status Code: 500 Internal Server Error </b>

      ```json
      {
          "error": "true",
          "message": "there is an error"
      }
      ```


## Report Lost

```
PUT /report/lost
Bearer Token
```

This is endpoint to report lost pet.

#### Request

- <b> Body JSON </b>

  - user_id as int
  - pet_id as int
  - pet_name as Varchar
  - gender as Enum ["Male / Female / Unknown"]
  - date_lost_found as timestamp
  - reward as decimal
  - province as Varchar
  - regency as Varchar
  - found_area as Varchar
  - email as Varchar
  - phone_number as Varchar
  - detail as enum ["lost / found"]
  - status as int (default : 0')

#### Response

- <b> Success Response </b>

  - Status Code: 200 OK

    ```json
    {
          "error": "false",
          "message": "Entri hewan hilang diperbarui dengan sukses."
    }
    ```

- <b> Error Responses </b>

  - <b> Status Code: 500 Internal Server Error </b>

      ```json
      {
          "error": "true",
          "message": "Error processing report/lost."
      }
      ```


## Report Found

```
PUT /report/found
Bearer Token
```

This is endpoint to report found pet.

#### Request

- <b> Body JSON </b>

  - user_id as int
  - pet_id as int
  - pet_name as Varchar
  - gender as Enum ["Male / Female / Unknown"]
  - date_lost_found as timestamp
  - reward as decimal
  - province as Varchar
  - regency as Varchar
  - found_area as Varchar
  - email as Varchar
  - phone_number as Varchar
  - detail as enum ["lost / found"]
  - status as int (default : 0')

#### Response

- <b> Success Response </b>

  - Status Code: 200 OK

    ```json
    {
          "error": "false",
          "message": "Hewan ditemukan dilaporkan dengan sukses"
    }
    ```

- <b> Error Responses </b>

  - <b> Status Code: 500 Internal Server Error </b>

      ```json
      {
          "error": "true",
          "message": "Ada kesalahan pada server",
          "errorDetails": "err.message"
      }
      ```


## Post ( Show Data by User ID )

```
GET /posts/:id
(ex: /posts/17)
```

This is endpoint to show data by user id.

#### Request

- <b> Body JSON </b>

  - Bearer Token

#### Response

- <b> Success Response </b>

  - Status Code: 200 OK

    ```json
    {
          "pet_id": 35,
          "user_id": 35,
          "pet_name": "apa aja",
          "type": "Cat",
          "gender": "Male",
          "date_lost_found": "2023-12-12T00:00:00.000Z",
          "reward": "100.00",
          "image_url": "https://storage.googleapis.com/path/to/images.jpg",
          "province": "Provinsi",
          "regency": "Kabupaten/Kota",
          "found_area": "Daerah Temuan",
          "email": "Email Pemilik",
          "phone_number": "Nomor Telepon Pemilik",
          "detail": "lost"

    }
    ```

- <b> Error Responses </b>

  - <b> Status Code: 500 Internal Server Error </b>

      ```json
      {
          "error": "true",
          "message": "there is an error"
      }
      ```


## Update Pet Status

```
PUT /pet/status/
(ex: /pet/status/17)
```

This is endpoint to update pet status.

#### Request

- <b> Body JSON </b>

  - Bearer Token

#### Response

- <b> Success Response </b>

  - Status Code: 200 OK

    ```json
    {
          "error": false,
          "message": "Status hewan berhasil diperbarui"
    }
    ```

- <b> Error Responses </b>

  - <b> Status Code: 500 Internal Server Error </b>

      ```json
      {
          "error": "true",
          "message": "there is an error"
      }
      ```


## Delete by ID

```
DELETE /pet/:id
(ex: /pet/17)
```

This is endpoint to delete pet by id.

#### Request

- <b> Body JSON </b>

  - Bearer Token

#### Response

- <b> Success Response </b>

  - Status Code: 200 OK

    ```json
    {
          "error": false,
          "message": "Pet deleted successfully"

    }
    ```

- <b> Error Responses </b>

  - <b> Status Code: 404 Not Found </b>

      ```json
      {
          "error": "true",
          "message": "Pet not found"
      }
      ```

  - <b> Status Code: 500 Internal Server Error </b>

      ```json
      {
          "error": "true",
          "message": "there is an error"
      }
      ```
