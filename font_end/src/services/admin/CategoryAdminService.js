import axios from "axios";

const ADMIN_CATEGORY_API_BASE_URL =
  "http://localhost:8080/api/v1/admin/category";

class CategoryAdminService {
  async createCategory(token, categoryRequest) {
    const response = await axios
      .post(ADMIN_CATEGORY_API_BASE_URL, categoryRequest, {
        headers: {
          Authorization: "Bearer " + token,
        },
      })
      .then(function (response) {
        console.log(response);
        return response;
      })
      .catch(function (error) {
        console.log(error);
      });
    return response;
  }

  async updateCategory(token, categoryId, categoryRequest) {
    const response = await axios
      .put(ADMIN_CATEGORY_API_BASE_URL + "/" + categoryId, categoryRequest, {
        headers: {
          Authorization: "Bearer " + token,
        },
      })
      .then(function (response) {
        console.log(response);
        return response;
      })
      .catch(function (error) {
        console.log(error);
      });
    return response;
  }

  async deleteCategory(token, categoryId) {
    const response = await axios
      .delete(ADMIN_CATEGORY_API_BASE_URL + "/" + categoryId, {
        headers: {
          Authorization: "Bearer " + token,
        },
      })
      .then(function (response) {
        console.log(response);
        return response;
      })
      .catch(function (error) {
        console.log(error);
      });
    return response;
  }
}

export default new CategoryAdminService();
