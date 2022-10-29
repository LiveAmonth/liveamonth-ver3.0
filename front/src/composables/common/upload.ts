import UploadApiService from "@/services/upload/UploadApiService";

export const useUpload = () => {
  const transformData = (name: string, file: File): FormData => {
    const formData = new FormData();
    formData.append(name, file, file.name);
    return formData;
  };

  const uploadImage = async (name: string, file: File) => {
    const data = transformData(name, file);
    console.log(data);
    return await UploadApiService.uploadImage(name, data);
  };
  return {
    uploadImage,
  };
};
