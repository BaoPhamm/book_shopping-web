import React, { useState, useEffect } from "react";
import BookAdminService from "../../services/admin/BookAdminService";
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
import Button from "@mui/material/Button";
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

const onUpdateBookSubmit = (event) => {
  event.preventDefault(event);
  console.log("onUpdateBookSubmit");
};
const onAddCatToBookSubmit = (event) => {
  console.log("onAddCatToBookSubmit");
  event.preventDefault(event);
};
const onRemoveCatFromBookSubmit = (event) => {
  console.log("onRemoveCatFromBookSubmit");
  event.preventDefault(event);
};

const ProductsList = (props) => {
  const [allProducts, setAllProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const tableClasses = useStyles();

  useEffect(() => {
    GetAllBooks();
  }, []);

  const GetAllBooks = async () => {
    await setIsLoading(true);
    BookAdminService.getAllBooks().then(async (res) => {
      await setAllProducts([...res]);
      await setIsLoading(false);
    });
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
                  />
                  <PopupContainer
                    onSubmit={onAddCatToBookSubmit}
                    typeSubmit="addCatToBook"
                  />
                  <PopupContainer
                    onSubmit={onRemoveCatFromBookSubmit}
                    typeSubmit="removeCatFromBook"
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
