import React from "react";
import Button from "@mui/material/Button";

const AddRoleToUserButton = ({ buttonRef, showModal, isAdminManager }) => {
  return (
    <Button
      variant="contained"
      color="warning"
      ref={buttonRef}
      disabled={!isAdminManager}
      onClick={showModal}
    >
      -ROLE
    </Button>
  );
};
export default AddRoleToUserButton;
