import React, { useState, useEffect } from "react";
import BookAdminService from "../../services/admin/BookAdminService";
import CategoryService from "../../services/user/CategoryService";
import { PopupContainer } from "../PopupForm/Container/index";
import { styled } from "@mui/material/styles";
import { makeStyles } from "@material-ui/styles";
import Table from "@mui/material/Table";
import TablePagination from "@mui/material/TablePagination";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import uploadImage from "../../firebase/upload";

const Container = styled("div")(() => ({
  display: "flex",
  flexDirection: "column",
}));

const AddBookButtonContainer = styled("div")(() => ({
  display: "flex",
  marginBottom: "1rem",
}));

const ButtonContainer = styled("div")(() => ({
  display: "flex",
}));

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
    fontSize: 13,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 13,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  "&:nth-of-type(odd)": {
    backgroundColor: theme.palette.action.hover,
  },
  // hide last border
  "&:last-child td, &:last-child th": {
    border: 0,
  },
}));

const useStyles = makeStyles({
  customTableCell: {
    "& .MuiTableCell-sizeSmall": {
      padding: "5px 5px 5px 5px",
    },
  },
});

const columns = [
  { id: "id", label: "ID", width: "2%", align: "center" },
  { id: "title", label: "Title", width: "14%", align: "center" },
  {
    id: "author",
    label: "Author",
    align: "center",
    width: "10%",
  },
  {
    id: "totalPages",
    label: "TotalPage",
    align: "center",
    width: "4%",
  },
  {
    id: "requiredAge",
    label: "ReqAge",
    align: "center",
    width: "4%",
  },
  {
    id: "releaseDate",
    label: "ReleaseDate",
    align: "center",
    width: "6%",
  },
  {
    id: "price",
    label: "Price",
    align: "center",
    width: "5%",
  },
  {
    id: "ratingPoint",
    label: "RatingPoint",
    align: "center",
    width: "4%",
  },
  {
    id: "totalRatings",
    label: "TotalRating",
    align: "center",
    width: "4%",
  },
  {
    id: "categories",
    label: "Categories",
    align: "center",
    width: "8%",
  },
  {
    id: "actions",
    label: "Actions",
    align: "center",
    width: "5%",
  },
];

const ProductsList = () => {
  const [allProducts, setAllProducts] = useState([]);
  const [allCategories, setAllCategories] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [dataChange, toggleDataChange] = useState(false);
  const [pageNumber, setPageNumber] = useState(0);
  const [totalPtoducts, setTotalPtoducts] = useState(0);
  const tableClasses = useStyles();
  const rowsPerPage = 25;

  useEffect(() => {
    GetAllBooks();
    GetAllCategories();
    GetTotalPtoducts();
  }, [dataChange]);

  const GetAllBooks = async () => {
    await setIsLoading(true);
    BookAdminService.getAllBooks(pageNumber).then(async (res) => {
      await setAllProducts([...res]);
    });
  };
  const GetAllCategories = async () => {
    CategoryService.getAllCategories().then(async (res) => {
      await setAllCategories([...res]);
    });
  };
  const GetTotalPtoducts = async () => {
    BookAdminService.getTotalBooks().then(async (res) => {
      await setTotalPtoducts(res.data);
      await setIsLoading(false);
    });
  };

  const onAddBookSubmit = async (event) => {
    event.preventDefault(event);
    let urlImage = "";
    let bookImage = event.target.imgFile.files[0];

    if (bookImage != null) {
      urlImage = await uploadImage("/book_img", bookImage);
    }
    console.log(urlImage);
    const bookRequest = JSON.stringify({
      title: event.target.title.value,
      author: event.target.author.value,
      totalPages: event.target.totalPages.value,
      requiredAge: event.target.requiredAge.value,
      releaseDate: event.target.releaseDate.value,
      price: event.target.price.value,
      description: event.target.description.value,
      imgSrc: urlImage,
    });

    BookAdminService.saveBook(bookRequest).then(async (res) => {
      if (res.status === 400) {
        alert(res.data.message);
      } else if (res.status === 200) {
        alert("Book successfully added!");
        await toggleDataChange(!dataChange);
      } else if (res.status === 403) {
        alert("Please login again!");
        await toggleDataChange(!dataChange);
      }
    });
  };

  const onUpdateBookSubmit = async (event) => {
    event.preventDefault(event);

    let urlImage = event.target.imgURL.value;
    let bookImage = event.target.imgFile.files[0];

    if (bookImage != null) {
      urlImage = await uploadImage("/book_img", bookImage);
    }
    console.log(urlImage);
    const bookRequest = JSON.stringify({
      id: event.target.id.value,
      title: event.target.title.value,
      author: event.target.author.value,
      totalPages: event.target.totalPages.value,
      requiredAge: event.target.requiredAge.value,
      releaseDate: event.target.releaseDate.value,
      price: event.target.price.value,
      description: event.target.description.value,
      imgSrc: urlImage,
    });

    BookAdminService.updateBook(bookRequest).then(async (res) => {
      if (res.status === 400) {
        alert(res.data.message);
      } else if (res.status === 200) {
        alert("Book datails successfully changed!");
        await toggleDataChange(!dataChange);
      } else if (res.status === 403) {
        alert("Please login again!");
        await toggleDataChange(!dataChange);
      }
    });
  };

  const onAddCatToBookSubmit = async (event) => {
    event.preventDefault(event);

    let categoriesIdAddList = [];
    allCategories.map((category) => {
      if (event.target["Id" + category.id].checked) {
        categoriesIdAddList.push(category.id);
      }
      return 0;
    });
    const bookRequest = JSON.stringify({
      bookId: event.target.id.value,
      categoriesId: categoriesIdAddList,
    });
    console.log(bookRequest);
    if (categoriesIdAddList.length !== 0) {
      BookAdminService.addCategoriesToBook(bookRequest).then(async (res) => {
        console.log(res);
        if (res.status === 200) {
          alert("Categories successfully added to book");
          await toggleDataChange(!dataChange);
        } else if (res.status === 400) {
          alert(res.data.message);
        } else if (res.status === 403) {
          alert("Please login again!");
          await toggleDataChange(!dataChange);
        }
      });
    } else {
      alert("Please select atleast 1 category to add.");
      await toggleDataChange(!dataChange);
    }
  };

  const onRemoveCatFromBookSubmit = async (event) => {
    event.preventDefault(event);

    let targetBook = allProducts.find(
      (book) => book.id === parseInt(event.target.id.value)
    );
    let categoriesIdRemoveList = [];
    targetBook.categories.map((category) => {
      if (event.target["Id" + category.id].checked) {
        categoriesIdRemoveList.push(category.id);
      }
      return 0;
    });
    const bookRequest = JSON.stringify({
      bookId: event.target.id.value,
      categoriesId: categoriesIdRemoveList,
    });
    console.log(bookRequest);
    if (categoriesIdRemoveList.length !== 0) {
      BookAdminService.removeCategoriesFromBook(bookRequest).then(
        async (res) => {
          console.log(res);
          if (res.status === 200) {
            alert("Categories successfully removed from book ");
            await toggleDataChange(!dataChange);
          } else if (res.status === 400) {
            alert(res.data.message);
          } else if (res.status === 403) {
            alert("Please login again!");
            await toggleDataChange(!dataChange);
          }
        }
      );
    } else {
      alert("Please select atleast 1 category to remove.");
      await toggleDataChange(!dataChange);
    }
  };
  const onDeleteBookSubmit = (event) => {
    event.preventDefault(event);

    BookAdminService.deleteBook(event.target.id.value).then(async (res) => {
      console.log(res);
      if (res.status === 200) {
        alert("Book successfully deleted.");
        await toggleDataChange(!dataChange);
      } else if (res.status === 400) {
        alert(res.data.message);
      } else if (res.status === 403) {
        alert("Please login again!");
        await toggleDataChange(!dataChange);
      }
    });
  };

  const handleChangePage = async (event, newPageNumber) => {
    await setPageNumber(newPageNumber);
    await toggleDataChange(!dataChange);
  };

  const getCategogiesString = (categories) => {
    let CategogiesString = "";
    categories.map((category) => (CategogiesString += category.name + " - "));
    return CategogiesString.slice(0, -2);
  };

  return !isLoading ? (
    <Container>
      <AddBookButtonContainer>
        <PopupContainer
          onSubmit={onAddBookSubmit}
          typeSubmitGroup="book"
          typeSubmit="addNew"
        />
      </AddBookButtonContainer>
      <Paper sx={{ width: "100%" }}>
        <TableContainer sx={{ maxHeight: "100vw" }}>
          <Table
            className="ProductListTable"
            stickyHeader
            size="small"
            classes={{ root: tableClasses.customTableCell }}
            aria-label="sticky table"
          >
            <TableHead>
              <StyledTableRow>
                {columns.map((column) => (
                  <StyledTableCell
                    key={column.id}
                    align={column.align}
                    style={{ width: column.width }}
                  >
                    {column.label}
                  </StyledTableCell>
                ))}
              </StyledTableRow>
            </TableHead>
            <TableBody>
              {allProducts.map((product, index) => (
                <StyledTableRow key={product.id} hover role="checkbox">
                  <StyledTableCell align="center" component="th" scope="row">
                    {index + pageNumber * rowsPerPage + 1}
                  </StyledTableCell>
                  <StyledTableCell align="center">
                    {product.title}
                  </StyledTableCell>
                  <StyledTableCell align="center">
                    {product.author}
                  </StyledTableCell>
                  <StyledTableCell align="center">
                    {product.totalPages}
                  </StyledTableCell>
                  <StyledTableCell align="center">
                    {product.requiredAge}
                  </StyledTableCell>
                  <StyledTableCell align="center">
                    {product.releaseDate}
                  </StyledTableCell>
                  <StyledTableCell align="center">
                    {product.price}
                  </StyledTableCell>
                  <StyledTableCell align="center">
                    {product.ratingPoint}
                  </StyledTableCell>
                  <StyledTableCell align="center">
                    {product.totalRatings}
                  </StyledTableCell>
                  <StyledTableCell align="center">
                    {getCategogiesString(product.categories)}
                  </StyledTableCell>
                  <StyledTableCell align="center">
                    <ButtonContainer
                      sx={{
                        "& .MuiButton-root": {
                          fontSize: 11,
                          padding: "1px 1px 1px 1px",
                          marginLeft: "1px",
                        },
                      }}
                    >
                      <PopupContainer
                        onSubmit={onUpdateBookSubmit}
                        typeSubmitGroup="book"
                        typeSubmit="update"
                        productDetails={product}
                      />
                      <PopupContainer
                        onSubmit={onAddCatToBookSubmit}
                        typeSubmitGroup="book"
                        typeSubmit="addCat"
                        productDetails={product}
                        allCategories={allCategories}
                      />
                      <PopupContainer
                        onSubmit={onRemoveCatFromBookSubmit}
                        typeSubmitGroup="book"
                        typeSubmit="removeCat"
                        productDetails={product}
                      />
                      <PopupContainer
                        onSubmit={onDeleteBookSubmit}
                        typeSubmitGroup="book"
                        typeSubmit="delete"
                        productDetails={product}
                      />
                    </ButtonContainer>
                  </StyledTableCell>
                </StyledTableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination
          rowsPerPageOptions={[]}
          component="div"
          count={totalPtoducts}
          rowsPerPage={rowsPerPage}
          page={pageNumber}
          onPageChange={handleChangePage}
          labelRowsPerPage="Rows per page"
          showFirstButton={true}
          showLastButton={true}
        />
      </Paper>
    </Container>
  ) : (
    ""
  );
};

export default ProductsList;
