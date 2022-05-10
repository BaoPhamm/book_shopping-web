import React from "react";
import Button from "@mui/material/Button";

const RemoveCatFromBookButton = ({ buttonRef, showModal }) => {
  return (
    <Button
      variant="contained"
      color="warning"
      ref={buttonRef}
      onClick={showModal}
    >
      -CAT
    </Button>
  );
};
export default RemoveCatFromBookButton;
