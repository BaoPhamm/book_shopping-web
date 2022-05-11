import React from "react";
import Button from "@mui/material/Button";

const UpdateRoleButton = ({ buttonRef, showModal, roleDetails }) => {
  return (
    <Button
      disabled={roleDetails.name === "ADMIN"}
      variant="contained"
      color="success"
      ref={buttonRef}
      onClick={showModal}
    >
      UPDATE
    </Button>
  );
};
export default UpdateRoleButton;
