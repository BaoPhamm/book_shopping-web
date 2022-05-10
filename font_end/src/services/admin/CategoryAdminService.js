import axios from "axios";
import AuthHeader from "../AuthHeader";

const ADMIN_CATEGORY_API_BASE_URL =
  "http://localhost:8080/api/v1/admin/category";

class CategoryAdminService {
  async createCategory(categoryRequest) {
    const response = await axios
      .post(ADMIN_CATEGORY_API_BASE_URL, categoryRequest, {
        headers: AuthHeader(),
      })
      .then(function (response) {
        console.log(response);
        return response;
      })
      .catch(function (error) {
        console.log(error);
        return error.response;
      });
    return response;
  }

  async updateCategory(categoryRequest) {
    const response = await axios
      .put(ADMIN_CATEGORY_API_BASE_URL, categoryRequest, {
        headers: AuthHeader(),
      })
      .then(function (response) {
        console.log(response);
        return response;
      })
      .catch(function (error) {
        console.log(error);
        return error.response;
      });
    return response;
  }

  async deleteCategory(categoryId) {
    const response = await axios
      .delete(ADMIN_CATEGORY_API_BASE_URL + "/" + categoryId, {
        headers: AuthHeader(),
      })
      .then(function (response) {
        console.log(response);
        return response;
      })
      .catch(function (error) {
        console.log(error);
        return error.response;
      });
    return response;
  }
}

export default new CategoryAdminService();
