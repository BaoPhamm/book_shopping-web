import axios from "axios";

const ADMIN_BOOK_API_BASE_URL = "http://localhost:8080/api/v1/admin/books";

class BookAdminService {
  async saveBook(token, bookRequest) {
    const response = await axios
      .post(ADMIN_BOOK_API_BASE_URL, bookRequest, {
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

  async addCategoriesToBook(token, addCategoryToBookRequest) {
    const response = await axios
      .post(
        ADMIN_BOOK_API_BASE_URL + "/category/add-to-book",
        addCategoryToBookRequest,
        {
          headers: {
            Authorization: "Bearer " + token,
          },
        }
      )
      .then(function (response) {
        console.log(response);
        return response;
      })
      .catch(function (error) {
        console.log(error);
      });
    return response;
  }

  async removeCategoriesFromBook(token, removeCategoryFromBookRequest) {
    const response = await axios
      .post(
        ADMIN_BOOK_API_BASE_URL + "/category/remove-from-book",
        removeCategoryFromBookRequest,
        {
          headers: {
            Authorization: "Bearer " + token,
          },
        }
      )
      .then(function (response) {
        console.log(response);
        return response;
      })
      .catch(function (error) {
        console.log(error);
      });
    return response;
  }

  async updateBook(token, bookRequest) {
    const response = await axios
      .put(ADMIN_BOOK_API_BASE_URL, bookRequest, {
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

  async deleteBook(token, bookId) {
    const response = await axios
      .delete(ADMIN_BOOK_API_BASE_URL + "/" + bookId, {
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

export default new BookAdminService();
