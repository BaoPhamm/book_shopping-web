import React, { useState, useEffect } from "react";
import BookAdminService from "../../services/admin/BookAdminService";
import CategoryService from "../../services/user/CategoryService";
import { PopupContainer } from "../PopupForm/Container/index";
import { styled } from "@mui/material/styles";
import { makeStyles } from "@material-ui/styles";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { ConnectedTvOutlined } from "@mui/icons-material";

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

const Select = styled("select")(() => ({
  width: "80%",
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

const ProductsList = () => {
  const [allProducts, setAllProducts] = useState([]);
  const [allCategories, setAllCategories] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [dataChange, toggleDataChange] = useState(false);
  const tableClasses = useStyles();

  useEffect(() => {
    GetAllBooks();
    GetAllCategories();
  }, [dataChange]);

  const GetAllBooks = async () => {
    await setIsLoading(true);
    BookAdminService.getAllBooks().then(async (res) => {
      console.log(res);
      await setAllProducts([...res]);
    });
  };
  const GetAllCategories = async () => {
    CategoryService.getAllCategories().then(async (res) => {
      await setAllCategories([...res]);
      await setIsLoading(false);
    });
  };

  const onAddBookSubmit = (event) => {
    event.preventDefault(event);

    const bookRequest = JSON.stringify({
      title: event.target.title.value,
      author: event.target.author.value,
      totalPages: event.target.totalPages.value,
      requiredAge: event.target.requiredAge.value,
      releaseDate: event.target.releaseDate.value,
      price: event.target.price.value,
      description: event.target.description.value,
      imgSrc: event.target.imgSrc.value,
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

  const onUpdateBookSubmit = (event) => {
    event.preventDefault(event);

    const bookRequest = JSON.stringify({
      id: event.target.id.value,
      title: event.target.title.value,
      author: event.target.author.value,
      totalPages: event.target.totalPages.value,
      requiredAge: event.target.requiredAge.value,
      releaseDate: event.target.releaseDate.value,
      price: event.target.price.value,
      description: event.target.description.value,
      imgSrc: event.target.imgSrc.value,
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

  return !isLoading ? (
    <Container>
      <AddBookButtonContainer>
        <PopupContainer onSubmit={onAddBookSubmit} typeSubmit="addBook" />
      </AddBookButtonContainer>
      <TableContainer component={Paper}>
        <Table
          size="small"
          classes={{ root: tableClasses.customTableCell }}
          aria-label="customized table"
        >
          <TableHead>
            <TableRow>
              <StyledTableCell align="center" style={{ width: "2%" }}>
                ID
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "18%" }}>
                Title
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "10%" }}>
                Author
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "4%" }}>
                TotalPage
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "4%" }}>
                ReqAge
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "6%" }}>
                ReleaseDate
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "5%" }}>
                Price
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "4%" }}>
                RatingPoint
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "4%" }}>
                TotalRating
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "7%" }}>
                Categories
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "5%" }}>
                Actions
              </StyledTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {allProducts.map((product) => (
              <StyledTableRow key={product.id}>
                <StyledTableCell align="center" omponent="th" scope="row">
                  {product.id}
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
                  <Select>
                    {product.categories.map((category) => (
                      <option value={category.id}>{category.name}</option>
                    ))}
                  </Select>
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
                      typeSubmit="updateBook"
                      productDetails={product}
                    />
                    <PopupContainer
                      onSubmit={onAddCatToBookSubmit}
                      typeSubmit="addCatToBook"
                      productDetails={product}
                      allCategories={allCategories}
                    />
                    <PopupContainer
                      onSubmit={onRemoveCatFromBookSubmit}
                      typeSubmit="removeCatFromBook"
                      productDetails={product}
                    />
                    <PopupContainer
                      onSubmit={onDeleteBookSubmit}
                      typeSubmit="deleteBook"
                      productDetails={product}
                    />
                  </ButtonContainer>
                </StyledTableCell>
              </StyledTableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Container>
  ) : (
    ""
  );
};

export default ProductsList;
