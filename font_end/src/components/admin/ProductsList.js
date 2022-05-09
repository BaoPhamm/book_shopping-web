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
import { Divider } from "@mui/material";

const ButtonContainer = styled(Divider)(() => ({
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
      await setAllProducts([...res]);
    });
  };
  const GetAllCategories = async () => {
    CategoryService.getAllCategories().then(async (res) => {
      await setAllCategories([...res]);
      await setIsLoading(false);
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
        if (res.data.errorMessage === "title blank") {
          alert("Please fill in the title field");
        } else if (res.data.errorMessage === "author blank") {
          alert("Please fill in the author field");
        } else if (res.data.errorMessage === "totalPages blank") {
          alert("Please fill in the total pages field");
        } else if (res.data.errorMessage === "requiredAge blank") {
          alert("Please fill in the required age field");
        } else if (res.data.errorMessage === "releaseDate blank") {
          alert("Please fill in the release date field");
        } else if (res.data.errorMessage === "price blank") {
          alert("Please fill in the price field");
        } else if (res.data.errorMessage === "description blank") {
          alert("Please fill in the description field");
        } else if (res.data.errorMessage === "imgSrc blank") {
          alert("Please fill in the image URL field");
        }
      } else if (res.status === 200) {
        alert("Book datails successfully changed!");
        await toggleDataChange(!dataChange);
      }
    });
  };
  const onAddCatToBookSubmit = (event) => {
    event.preventDefault(event);

    let categoriesIdAddList = [];
    allCategories.map((category) => {
      if (event.target["Id" + category.id].checked) {
        categoriesIdAddList.push(category.id);
      }
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
        }
      });
    } else {
      alert("Please select atleast 1 category to add.");
    }
  };

  const onRemoveCatFromBookSubmit = (event) => {
    event.preventDefault(event);

    let targetBook = allProducts.find(
      (book) => book.id === parseInt(event.target.id.value)
    );
    let categoriesIdRemoveList = [];
    targetBook.categories.map((category) => {
      if (event.target["Id" + category.id].checked) {
        categoriesIdRemoveList.push(category.id);
      }
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
          }
        }
      );
    } else {
      alert("Please select atleast 1 category to remove.");
    }
  };

  return !isLoading ? (
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
            <StyledTableCell align="center" style={{ width: "15%" }}>
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
            <StyledTableCell align="center" style={{ width: "8%" }}>
              ReleaseDate
            </StyledTableCell>
            <StyledTableCell align="center" style={{ width: "5%" }}>
              Price
            </StyledTableCell>
            <StyledTableCell align="center" style={{ width: "4%" }}>
              Rating
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
              <StyledTableCell align="center">{product.title}</StyledTableCell>
              <StyledTableCell align="center">{product.author}</StyledTableCell>
              <StyledTableCell align="center">
                {product.totalPages}
              </StyledTableCell>
              <StyledTableCell align="center">
                {product.requiredAge}
              </StyledTableCell>
              <StyledTableCell align="center">
                {product.releaseDate}
              </StyledTableCell>
              <StyledTableCell align="center">{product.price}</StyledTableCell>
              <StyledTableCell align="center">
                {product.ratingPoint}
              </StyledTableCell>
              <StyledTableCell align="center">
                <select>
                  {product.categories.map((category) => (
                    <option value={category.id}>{category.name}</option>
                  ))}
                </select>
              </StyledTableCell>
              <StyledTableCell align="center">
                <ButtonContainer
                  sx={{
                    "& .MuiButton-root": {
                      fontSize: 11,
                      padding: "2px 2px 2px 2px",
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
                </ButtonContainer>
              </StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  ) : (
    ""
  );
};

export default ProductsList;
