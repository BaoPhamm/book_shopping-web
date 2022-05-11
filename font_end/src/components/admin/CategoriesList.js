import React, { useState, useEffect } from "react";
import CategoryAdminService from "../../services/admin/CategoryAdminService";
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

const Container = styled("div")(() => ({
  display: "flex",
  flexDirection: "column",
}));

const AddRoleButtonContainer = styled("div")(() => ({
  display: "flex",
  marginBottom: "1rem",
}));

const ButtonContainer = styled("div")(() => ({
  display: "flex",
  justifyContent: "center",
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

const CategoriesList = () => {
  const [allCategories, setAllCategories] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [dataChange, toggleDataChange] = useState(false);
  const tableClasses = useStyles();

  useEffect(() => {
    GetAllCategories();
  }, [dataChange]);

  const GetAllCategories = async () => {
    await setIsLoading(true);
    CategoryService.getAllCategories().then(async (res) => {
      await setAllCategories([...res]);
      await setIsLoading(false);
    });
  };

  const onAddNewCategorySubmit = async (event) => {
    event.preventDefault(event);

    const categoryRequest = JSON.stringify({
      name: event.target.categoryname.value,
      description: event.target.description.value,
      imgSrc: event.target.imgsrc.value,
    });
    CategoryAdminService.createCategory(categoryRequest).then(async (res) => {
      if (res.status === 400) {
        alert(res.data.message);
      } else if (res.status === 200) {
        alert("Category successfully added!");
        await toggleDataChange(!dataChange);
      } else if (res.status === 403) {
        alert("Please login again!");
        await toggleDataChange(!dataChange);
      }
    });
  };

  const onUpdateCategorySubmit = async (event) => {
    event.preventDefault(event);

    const categoryRequest = JSON.stringify({
      id: event.target.categoryid.value,
      name: event.target.categoryname.value,
      description: event.target.description.value,
      imgSrc: event.target.imgsrc.value,
    });

    CategoryAdminService.updateCategory(categoryRequest).then(async (res) => {
      if (res.status === 400 || res.status === 404) {
        alert(res.data.message);
      } else if (res.status === 200) {
        alert("Category successfully updated!");
        await toggleDataChange(!dataChange);
      } else if (res.status === 403) {
        alert("Please login again!");
        await toggleDataChange(!dataChange);
      }
    });
  };

  const onDeleteCategorySubmit = async (event) => {
    event.preventDefault(event);

    CategoryAdminService.deleteCategory(event.target.categoryid.value).then(
      async (res) => {
        if (res.status === 400 || res.status === 404) {
          alert(res.data.message);
        } else if (res.status === 200) {
          alert("Category successfully deleted!");
          await toggleDataChange(!dataChange);
        } else if (res.status === 403) {
          alert("Please login again!");
          await toggleDataChange(!dataChange);
        }
      }
    );
  };

  return !isLoading ? (
    <Container>
      <AddRoleButtonContainer>
        <PopupContainer
          onSubmit={onAddNewCategorySubmit}
          typeSubmit="addNewCategory"
        />
      </AddRoleButtonContainer>
      <TableContainer component={Paper}>
        <Table
          size="small"
          classes={{ root: tableClasses.customTableCell }}
          aria-label="customized table"
        >
          <TableHead>
            <TableRow>
              <StyledTableCell align="center" style={{ width: "5%" }}>
                ID
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "15%" }}>
                Name
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "30%" }}>
                Description
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "30%" }}>
                Image URL
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "20%" }}>
                Actions
              </StyledTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {allCategories.map((category) => (
              <StyledTableRow key={category.id}>
                <StyledTableCell align="center" omponent="th" scope="row">
                  {category.id}
                </StyledTableCell>
                <StyledTableCell align="center">
                  {category.name}
                </StyledTableCell>
                <StyledTableCell align="center">
                  {category.description}
                </StyledTableCell>
                <StyledTableCell align="center">
                  {category.imgSrc}
                </StyledTableCell>
                <StyledTableCell align="center">
                  <ButtonContainer
                    sx={{
                      "& .MuiButton-root": {
                        fontSize: 11,
                        padding: "1px 1px 1px 1px",
                        marginLeft: "1px",
                      },
                      "& .MuiFormGroup-root": {
                        padding: "1px 1px 1px 1px",
                        marginLeft: "10px",
                      },
                    }}
                  >
                    <PopupContainer
                      onSubmit={onUpdateCategorySubmit}
                      typeSubmit="updateCategory"
                      categoryDetails={category}
                    />
                    <PopupContainer
                      onSubmit={onDeleteCategorySubmit}
                      typeSubmit="deleteCategory"
                      categoryDetails={category}
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

export default CategoriesList;
