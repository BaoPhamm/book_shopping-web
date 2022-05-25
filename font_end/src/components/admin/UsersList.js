import React, { useState, useEffect } from "react";
import RoleAdminService from "../../services/manageAdmin/RoleAdminService";
import UserAdminService from "../../services/admin/UserAdminService";
import ManageAdminService from "../../services/manageAdmin/ManageAdminService";
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
import { useSelector } from "react-redux";
import { loginSelector } from "../../store/reducers/loginSlice";

const Container = styled("div")(() => ({
  display: "flex",
  flexDirection: "column",
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
  { id: "username", label: "Username", width: "8%", align: "center" },
  {
    id: "firstName",
    label: "First name",
    align: "center",
    width: "8%",
  },
  {
    id: "lastName",
    label: "Last name",
    align: "center",
    width: "8%",
  },
  {
    id: "phoneNumber",
    label: "Phone number",
    align: "center",
    width: "8%",
  },
  {
    id: "address",
    label: "Address",
    align: "center",
    width: "15%",
  },
  {
    id: "roles",
    label: "Roles",
    align: "center",
    width: "10%",
  },
  {
    id: "actions",
    label: "Actions",
    align: "center",
    width: "5%",
  },
];

const UsersList = () => {
  const [allUsers, setAllUsers] = useState([]);
  const [allRoles, setAllRoles] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [dataChange, toggleDataChange] = useState(false);
  const [pageNumber, setPageNumber] = useState(0);
  const [totalUsers, setTotalUsers] = useState(0);
  const rowsPerPage = 25;
  const tableClasses = useStyles();
  const loginInfo = useSelector(loginSelector);

  useEffect(() => {
    GetAllUsers();
    GetAllRoles();
    GetTotalUsers();
  }, [dataChange]);

  const GetAllUsers = async () => {
    await setIsLoading(true);
    UserAdminService.getAllUsers(pageNumber).then(async (res) => {
      await setAllUsers([...res]);
    });
  };
  const GetAllRoles = async () => {
    RoleAdminService.getAllRoles().then(async (res) => {
      await setAllRoles([...res]);
    });
  };
  const GetTotalUsers = async () => {
    UserAdminService.getTotalUsers().then(async (res) => {
      await setTotalUsers(res.data);
      await setIsLoading(false);
    });
  };

  const onAddRoleToUserSubmit = async (event) => {
    event.preventDefault(event);

    let rolesIdAddList = [];
    allRoles.map((role) => {
      if (event.target["Id" + role.id].checked) {
        rolesIdAddList.push(role.id);
      }
      return 0;
    });
    const userAddRoleRequest = JSON.stringify({
      userId: event.target.id.value,
      rolesId: rolesIdAddList,
    });
    console.log(userAddRoleRequest);
    if (rolesIdAddList.length !== 0) {
      ManageAdminService.addRoleToUser(userAddRoleRequest).then(async (res) => {
        if (res.status === 200) {
          alert("Roles successfully added to user");
          await toggleDataChange(!dataChange);
        } else if (res.status === 400) {
          alert(res.data.message);
        } else if (res.status === 403) {
          alert("Please login again!");
          await toggleDataChange(!dataChange);
        }
      });
    } else {
      alert("Please select atleast 1 role to add.");
      await toggleDataChange(!dataChange);
    }
  };

  const onRemoveRoleFromUserSubmit = async (event) => {
    event.preventDefault(event);

    let targetUser = allUsers.find(
      (book) => book.id === parseInt(event.target.id.value)
    );
    let rolesIdRemoveList = [];
    targetUser.roles.map((role) => {
      if (event.target["Id" + role.id].checked) {
        rolesIdRemoveList.push(role.id);
      }
      return 0;
    });
    const userRemoveRoleRequest = JSON.stringify({
      userId: event.target.id.value,
      rolesId: rolesIdRemoveList,
    });
    console.log(userRemoveRoleRequest);
    if (rolesIdRemoveList.length !== 0) {
      ManageAdminService.removeRoleFromUser(userRemoveRoleRequest).then(
        async (res) => {
          if (res.status === 200) {
            alert("Roles successfully removed from user.");
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
      alert("Please select atleast 1 role to remove.");
      await toggleDataChange(!dataChange);
    }
  };

  const onDeleteUserSubmit = (event) => {
    event.preventDefault(event);

    console.log(event.target.id.value);
    UserAdminService.deleteUser(event.target.id.value).then(async (res) => {
      console.log(res);
      if (res.status === 200) {
        alert("User successfully deleted.");
        await toggleDataChange(!dataChange);
      } else if (res.status === 400) {
        alert(res.data.message);
      } else if (res.status === 405) {
        alert(res.data.message);
      } else if (res.status === 403) {
        alert("Please login again!");
        await toggleDataChange(!dataChange);
      }
    });
  };

  const onBlockOrUnBlockUserSubmit = async (event) => {
    event.preventDefault(event);

    if (event.target.blockstatus.value === "false") {
      UserAdminService.blockUser(event.target.id.value).then(async (res) => {
        if (res.status === 200) {
          alert("User successfully blocked.");
          await toggleDataChange(!dataChange);
        } else if (res.status === 404 || res.status === 405) {
          alert(res.data.message);
        } else if (res.status === 403) {
          alert("Please login again!");
          await toggleDataChange(!dataChange);
        }
      });
    } else if (event.target.blockstatus.value === "true") {
      UserAdminService.unBlockUser(event.target.id.value).then(async (res) => {
        if (res.status === 200) {
          alert("User successfully unblocked.");
          await toggleDataChange(!dataChange);
        } else if (res.status === 404 || res.status === 405) {
          alert(res.data.message);
        } else if (res.status === 403) {
          alert("Please login again!");
          await toggleDataChange(!dataChange);
        }
      });
    }
  };

  const handleChangePage = async (event, newPageNumber) => {
    await setPageNumber(newPageNumber);
    await toggleDataChange(!dataChange);
  };

  const getRolesString = (roles) => {
    let rolesString = "";
    roles.map((role) => (rolesString += role.name + " - "));
    return rolesString.slice(0, -2);
  };

  return !isLoading ? (
    <Container>
      <Paper sx={{ width: "100%" }}>
        <TableContainer sx={{ maxHeight: "100vw" }}>
          <Table
            size="small"
            classes={{ root: tableClasses.customTableCell }}
            aria-label="customized table"
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
              {allUsers.map((user, index) => (
                <StyledTableRow key={user.id}>
                  <StyledTableCell align="center" omponent="th" scope="row">
                    {index + 1}
                  </StyledTableCell>
                  <StyledTableCell align="center">
                    {user.username}
                  </StyledTableCell>
                  <StyledTableCell align="center">
                    {user.firstName}
                  </StyledTableCell>
                  <StyledTableCell align="center">
                    {user.lastName}
                  </StyledTableCell>
                  <StyledTableCell align="center">
                    {user.phoneNumber}
                  </StyledTableCell>
                  <StyledTableCell align="center">
                    {user.address}
                  </StyledTableCell>
                  <StyledTableCell align="center">
                    {getRolesString(user.roles)}
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
                        "& .MuiTypography-root": {
                          fontSize: 13,
                        },
                      }}
                    >
                      {loginInfo.isAdminManager ? (
                        <PopupContainer
                          onSubmit={onAddRoleToUserSubmit}
                          typeSubmitGroup="user"
                          typeSubmit="addRole"
                          userDetails={user}
                          isAdminManager={loginInfo.isAdminManager}
                          allRoles={allRoles}
                        />
                      ) : (
                        ""
                      )}
                      {loginInfo.isAdminManager ? (
                        <PopupContainer
                          onSubmit={onRemoveRoleFromUserSubmit}
                          typeSubmitGroup="user"
                          typeSubmit="removeRole"
                          userDetails={user}
                          isAdminManager={loginInfo.isAdminManager}
                        />
                      ) : (
                        ""
                      )}

                      <PopupContainer
                        onSubmit={onDeleteUserSubmit}
                        typeSubmitGroup="user"
                        typeSubmit="delete"
                        userDetails={user}
                      />
                      <PopupContainer
                        onSubmit={onBlockOrUnBlockUserSubmit}
                        typeSubmitGroup="user"
                        typeSubmit="block"
                        userDetails={user}
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
          count={totalUsers}
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

export default UsersList;
