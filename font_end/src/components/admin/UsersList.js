import React, { useState, useEffect } from "react";
import RoleAdminService from "../../services/admin/RoleAdminService";
import UserAdminService from "../../services/admin/UserAdminService";
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

const UsersList = () => {
  const [allUsers, setAllUsers] = useState([]);
  const [allRoles, setAllRoles] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [dataChange, toggleDataChange] = useState(false);
  const tableClasses = useStyles();

  useEffect(() => {
    GetAllUsers();
    GetAllRoles();
  }, [dataChange]);

  const GetAllUsers = async () => {
    await setIsLoading(true);
    UserAdminService.getAllUsers().then(async (res) => {
      await setAllUsers([...res]);
    });
  };
  const GetAllRoles = async () => {
    RoleAdminService.getAllRoles().then(async (res) => {
      await setAllRoles([...res]);
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
      UserAdminService.addRoleToUser(userAddRoleRequest).then(async (res) => {
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
      UserAdminService.removeRoleFromUser(userRemoveRoleRequest).then(
        async (res) => {
          if (res.status === 200) {
            alert("Categories successfully removed from user ");
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
          alert("User successfully unblocked.");
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

  return !isLoading ? (
    <Container>
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
              <StyledTableCell align="center" style={{ width: "8%" }}>
                Username
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "8%" }}>
                First name
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "8%" }}>
                Last name
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "8%" }}>
                Phone number
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "20%" }}>
                Address
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "8%" }}>
                Roles
              </StyledTableCell>
              <StyledTableCell align="center" style={{ width: "5%" }}>
                Actions
              </StyledTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {allUsers.map((user) => (
              <StyledTableRow key={user.id}>
                <StyledTableCell align="center" omponent="th" scope="row">
                  {user.id}
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
                <StyledTableCell align="center">{user.address}</StyledTableCell>
                <StyledTableCell align="center">
                  <select>
                    {user.roles.map((role) => (
                      <option value={role.id}>{role.name}</option>
                    ))}
                  </select>
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
                    <PopupContainer
                      onSubmit={onAddRoleToUserSubmit}
                      typeSubmit="addRoleToUser"
                      userDetails={user}
                      allRoles={allRoles}
                    />
                    <PopupContainer
                      onSubmit={onRemoveRoleFromUserSubmit}
                      typeSubmit="removeRoleFromUser"
                      userDetails={user}
                    />
                    <PopupContainer
                      onSubmit={onDeleteUserSubmit}
                      typeSubmit="deleteUser"
                      userDetails={user}
                    />
                    <PopupContainer
                      onSubmit={onBlockOrUnBlockUserSubmit}
                      typeSubmit="blockUser"
                      userDetails={user}
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

export default UsersList;
