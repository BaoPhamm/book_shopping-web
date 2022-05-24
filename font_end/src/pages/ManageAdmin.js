import React, { useState, useEffect } from "react";
import UsersList from "../components/admin/UsersList";
import RolesList from "../components/admin/RolesList";
import AdminsList from "../components/admin/AdminsList";
import styled from "styled-components";
import Box from "@mui/material/Box";
import Tab from "@mui/material/Tab";
import TabContext from "@mui/lab/TabContext";
import TabList from "@mui/lab/TabList";
import TabPanel from "@mui/lab/TabPanel";
import { useSelector } from "react-redux";
import { loginSelector } from "../store/reducers/loginSlice";
import { Navigate } from "react-router-dom";

const Container = styled.div`
  margin-top: 1rem;
`;

const ManageAdmin = () => {
  const [value, setValue] = useState("1");
  const loginInfo = useSelector(loginSelector);

  useEffect(() => {}, [loginInfo.isLogged]);

  const handleChange = async (event, newValue) => {
    event.preventDefault();
    await setValue(newValue);
  };
  return !loginInfo.isLoading ? (
    loginInfo.isAdminManager ? (
      <Container>
        <Box
          sx={{
            width: "100%",
            typography: "body1",
            "& .MuiTabPanel-root": {
              padding: "15px 5px 5px 5px",
            },
            "& .MuiTableContainer-root": {
              padding: "0px 0px 0px 0px",
              maxHeight: "70vh",
            },
          }}
        >
          <TabContext value={value}>
            <Box
              sx={{
                borderBottom: 1,
                borderColor: "divider",
              }}
            >
              <TabList
                onChange={handleChange}
                aria-label="BookStore admin management page"
                centered
              >
                <Tab label="ADMINS" value="1" />
                <Tab label="USERS" value="2" />
                <Tab label="ROLES" value="3" />
              </TabList>
            </Box>
            <TabPanel value="1">
              <AdminsList />
            </TabPanel>
            <TabPanel value="2">
              <UsersList />
            </TabPanel>
            <TabPanel value="3">
              <RolesList />
            </TabPanel>
          </TabContext>
        </Box>
      </Container>
    ) : (
      <Navigate to="/" />
    )
  ) : (
    ""
  );
};

export default ManageAdmin;
