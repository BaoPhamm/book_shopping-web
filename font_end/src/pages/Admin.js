import React, { useState } from "react";
import ProductsList from "../components/admin/ProductsList";
import styled from "styled-components";
import Box from "@mui/material/Box";
import Tab from "@mui/material/Tab";
import TabContext from "@mui/lab/TabContext";
import TabList from "@mui/lab/TabList";
import TabPanel from "@mui/lab/TabPanel";

const Container = styled.div`
  margin-top: 1rem;
`;

const Admin = () => {
  const [value, setValue] = useState("1");

  const handleChange = async (event, newValue) => {
    event.preventDefault();
    await setValue(newValue);
  };
  return (
    <Container>
      <Box
        sx={{
          width: "100%",
          typography: "body1",
          "& .MuiTabPanel-root": {
            padding: "15px 5px 5px 5px",
          },
        }}
      >
        <TabContext value={value}>
          <Box
            sx={{
              borderBottom: 1,
              borderColor: "divider",
              "& .MuiTabPanel-root": {
                padding: "5px 5px 5px 5px",
              },
            }}
          >
            <TabList
              onChange={handleChange}
              aria-label="Admin management page"
              centered
            >
              <Tab label="BOOKS" value="1" />
              <Tab label="USERS" value="2" />
            </TabList>
          </Box>
          <TabPanel value="1">
            <ProductsList />
          </TabPanel>
          <TabPanel value="2">Item Two</TabPanel>
        </TabContext>
      </Box>
    </Container>
  );
};

export default Admin;
