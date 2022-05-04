import axios from "axios";

const CATEGORY_API_BASE_URL = "http://localhost:8080/api/v1/category";

class CategoryService {
  async getCategoryById(categoryId) {
    const response = await axios
      .get(CATEGORY_API_BASE_URL + "/" + categoryId)
      .then(function (response) {
        return response;
      })
      .catch(function (error) {
        console.log(error);
        return error.response;
      });
    return response;
  }

  async getCategoryByName(categoryName) {
    const response = await axios
      .get(CATEGORY_API_BASE_URL + "/" + categoryName)
      .then(function (response) {
        return response;
      })
      .catch(function (error) {
        console.log(error);
        return error.response;
      });
    return response;
  }

  async getAllCategories() {
    const response = await axios
      .get(CATEGORY_API_BASE_URL)
      .then(function (response) {
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
        return error.response;
      });
    return response;
  }
}

export default new CategoryService();
