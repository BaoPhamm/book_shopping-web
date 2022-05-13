import storage from "./index";

const uploadImage = async (path, imageAsFile) => {
  const uploadTask = await storage
    .ref(`${path}/${imageAsFile.name}`)
    .put(imageAsFile);

  let urlImage = await storage
    .ref(path)
    .child(imageAsFile.name)
    .getDownloadURL();
  return urlImage;
};

export default uploadImage;
