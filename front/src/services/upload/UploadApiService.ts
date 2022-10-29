import axios from "axios";

class UploadApiService {
  async uploadImage(fileName: string, file: FormData) {
    return axios
      .post(
        `https://api.imgbb.com/1/upload`,
        {
          key: "070876b123acb248037a79d0e0cbc8f1",
          image: file,
          name: fileName,
        },
        {
          withCredentials: true,
        }
      )
      .then((response) => {
        console.log(response);
        return response;
      })
      .catch((error) => {
        console.log(error);
      });
  }
}

export default new UploadApiService();
