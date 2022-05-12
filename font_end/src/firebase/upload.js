import storage from "./index";

const uploadImage = async (path, imageAsFile) => {
  const uploadTask = storage
    .ref(`${path}/${imageAsFile.name}`)
    .put(imageAsFile);

  //initiates the firebase side uploading
  await uploadTask.on(
    "state_changed",
    (snapShot) => {},
    (err) => {
      console.log(err);
    }
  );
  let urlImage = await storage
    .ref(path)
    .child(imageAsFile.name)
    .getDownloadURL();
  return urlImage;
};

export default uploadImage;
