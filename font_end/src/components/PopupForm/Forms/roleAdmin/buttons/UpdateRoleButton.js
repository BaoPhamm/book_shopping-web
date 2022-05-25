import React from "react";
import Button from "@mui/material/Button";

const UpdateRoleButton = ({ buttonRef, showModal, roleDetails }) => {
  return (
    <Button
      variant="contained"
      color="success"
      ref={buttonRef}
      disabled={roleDetails.name === "ADMIN" || roleDetails.name === "USER"}
      onClick={showModal}
    >
      UPDATE
    </Button>
  );
};
export default UpdateRoleButton;
