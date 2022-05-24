import React, { useState, useEffect } from "react";
import RoleAdminService from "../../services/manageAdmin/RoleAdminService";
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
  width: "50%",
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

const RolesList = () => {
  const [allRoles, setAllRoles] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [dataChange, toggleDataChange] = useState(false);
  const tableClasses = useStyles();

  useEffect(() => {
    GetAllRoles();
  }, [dataChange]);

  const GetAllRoles = async () => {
    await setIsLoading(true);
    RoleAdminService.getAllRoles().then(async (res) => {
      await setAllRoles([...res]);
      await setIsLoading(false);
    });
  };

  const onAddNewRoleSubmit = async (event) => {
    event.preventDefault(event);

    const roleRequest = JSON.stringify({
      name: event.target.rolename.value,
    });

    RoleAdminService.createRole(roleRequest).then(async (res) => {
      if (res.status === 400) {
        alert(res.data.message);
      } else if (res.status === 200) {
        alert("Role successfully added!");
        await toggleDataChange(!dataChange);
      } else if (res.status === 403) {
        alert("Please login again!");
        await toggleDataChange(!dataChange);
      }
    });
  };

  const onUpdateRoleSubmit = async (event) => {
    event.preventDefault(event);

    const roleRequest = JSON.stringify({
      id: event.target.roleid.value,
      name: event.target.rolename.value,
    });

    RoleAdminService.updateRole(roleRequest).then(async (res) => {
      if (res.status === 400 || res.status === 404) {
        alert(res.data.message);
      } else if (res.status === 200) {
        alert("Role successfully updated!");
        await toggleDataChange(!dataChange);
      } else if (res.status === 403) {
        alert("Please login again!");
        await toggleDataChange(!dataChange);
      }
    });
  };

  const onDeleteRoleSubmit = async (event) => {
    event.preventDefault(event);

    RoleAdminService.deleteRole(event.target.roleid.value).then(async (res) => {
      if (res.status === 400 || res.status === 404) {
        alert(res.data.message);
      } else if (res.status === 200) {
        alert("Role successfully deleted!");
        await toggleDataChange(!dataChange);
      } else if (res.status === 403) {
        alert("Please login again!");
        await toggleDataChange(!dataChange);
      }
    });
  };

  return !isLoading ? (
    <Container>
      <AddRoleButtonContainer>
        <PopupContainer
          onSubmit={onAddNewRoleSubmit}
          typeSubmitGroup="role"
          typeSubmit="addNew"
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
              <StyledTableCell align="center" style={{ width: "25%" }}>
                ID
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "25%" }}>
                Name
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "50%" }}>
                Actions
              </StyledTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {allRoles.map((role) => (
              <StyledTableRow key={role.id}>
                <StyledTableCell align="center" omponent="th" scope="row">
                  {role.id}
                </StyledTableCell>
                <StyledTableCell align="center">{role.name}</StyledTableCell>
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
                      onSubmit={onUpdateRoleSubmit}
                      typeSubmitGroup="role"
                      typeSubmit="update"
                      roleDetails={role}
                    />
                    <PopupContainer
                      onSubmit={onDeleteRoleSubmit}
                      typeSubmitGroup="role"
                      typeSubmit="delete"
                      roleDetails={role}
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

export default RolesList;
