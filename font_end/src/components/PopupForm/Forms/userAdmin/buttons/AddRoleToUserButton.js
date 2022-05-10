import React from "react";
import Button from "@mui/material/Button";

const AddRoleToUserButton = ({ buttonRef, showModal }) => {
  return (
    <Button
      variant="contained"
      color="success"
      ref={buttonRef}
      onClick={showModal}
    >
      +ROLE
    </Button>
  );
};
export default AddRoleToUserButton;
