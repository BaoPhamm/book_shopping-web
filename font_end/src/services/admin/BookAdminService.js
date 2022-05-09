import axios from "axios";
import AuthHeader from "../AuthHeader";

const ADMIN_BOOK_API_BASE_URL = "http://localhost:8080/api/v1/admin/books";

class BookAdminService {
  async getBookById(bookId) {
    const response = await axios
      .get(ADMIN_BOOK_API_BASE_URL + "/" + bookId, { headers: AuthHeader() })
      .then(function (response) {
        return response;
      })
      .catch(function (error) {
        console.log(error);
        return error.response;
      });
    return response;
  }

  async getAllBooks() {
    const response = await axios
      .get(ADMIN_BOOK_API_BASE_URL, { headers: AuthHeader() })
      .then(function (response) {
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
        return error.response;
      });
    return response;
  }

  async getFeaturesBooks() {
    const response = await axios
      .get(ADMIN_BOOK_API_BASE_URL + "/features", { headers: AuthHeader() })
      .then(function (response) {
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
        return error.response;
      });
    return response;
  }

  async getBooksByCategory(categoryId) {
    const response = await axios
      .get(ADMIN_BOOK_API_BASE_URL + "/category/" + categoryId, {
        headers: AuthHeader(),
      })
      .then(function (response) {
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
        return error.response;
      });
    return response;
  }

  async saveBook(bookRequest) {
    const response = await axios
      .post(ADMIN_BOOK_API_BASE_URL, bookRequest, {
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

  async addCategoriesToBook(addCategoryToBookRequest) {
    const response = await axios
      .post(
        ADMIN_BOOK_API_BASE_URL + "/category/add-to-book",
        addCategoryToBookRequest,
        {
          headers: AuthHeader(),
        }
      )
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

  async removeCategoriesFromBook(removeCategoryFromBookRequest) {
    const response = await axios
      .post(
        ADMIN_BOOK_API_BASE_URL + "/category/remove-from-book",
        removeCategoryFromBookRequest,
        {
          headers: AuthHeader(),
        }
      )
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

  async updateBook(bookRequest) {
    const response = await axios
      .put(ADMIN_BOOK_API_BASE_URL, bookRequest, {
        headers: AuthHeader(),
      })
      .then(function (response) {
        return response;
      })
      .catch(function (error) {
        console.log(error);
        return error.response;
      });
    return response;
  }

  async deleteBook(bookId) {
    const response = await axios
      .delete(ADMIN_BOOK_API_BASE_URL + "/" + bookId, {
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

export default new BookAdminService();
