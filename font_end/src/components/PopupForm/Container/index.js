import React, { Component } from "react";
import { Modal } from "../Modal";
import ChangPasswordButton from "../Forms/bookAdmin/buttons/ChangPasswordButton";
import AddBookButton from "../Forms/bookAdmin/buttons/AddBookButton";
import UpdateBookButton from "../Forms/bookAdmin/buttons/UpdateBookButton";
import AddCatToBookButton from "../Forms/bookAdmin/buttons/AddCatToBookButton";
import RemoveCatFromBookButton from "../Forms/bookAdmin/buttons/RemoveCatFromBookButton";
import DeleteBookButton from "../Forms/bookAdmin/buttons/DeleteBookButton";
import AddRoleToUserButton from "../Forms/userAdmin/buttons/AddRoleToUserButton";
import RemoveRoleFromUserButton from "../Forms/userAdmin/buttons/RemoveRoleFromUserButton";
import DeleteUserButton from "../Forms/userAdmin/buttons/DeleteUserButton";
import BlockUserSwitch from "../Forms/userAdmin/buttons/BlockUserSwitch";
import AddNewRoleButton from "../Forms/roleAdmin/buttons/AddNewRoleButton";
import UpdateRoleButton from "../Forms/roleAdmin/buttons/UpdateRoleButton";
import DeleteRoleButton from "../Forms/roleAdmin/buttons/DeleteRoleButton";
import "./index.css";

export class PopupContainer extends Component {
  state = { isShown: false };
  showModal = () => {
    this.setState({ isShown: true }, () => {
      this.closeButton.focus();
    });
    this.toggleScrollLock();
  };
  closeModal = () => {
    this.setState({ isShown: false });
    this.TriggerButton.focus();
    this.toggleScrollLock();
  };
  onKeyDown = (event) => {
    if (event.keyCode === 27) {
      this.closeModal();
    }
  };

  toggleScrollLock = () => {
    document.querySelector("html").classList.toggle("scroll-lock");
  };
  GetButton = () => {
    if (this.props.typeSubmit === "changePassword") {
      return (
        <ChangPasswordButton
          showModal={this.showModal}
          buttonRef={(n) => (this.TriggerButton = n)}
        />
      );
    }
    // BOOK
    else if (this.props.typeSubmit === "addBook") {
      return (
        <AddBookButton
          showModal={this.showModal}
          buttonRef={(n) => (this.TriggerButton = n)}
        />
      );
    } else if (this.props.typeSubmit === "updateBook") {
      return (
        <UpdateBookButton
          showModal={this.showModal}
          buttonRef={(n) => (this.TriggerButton = n)}
        />
      );
    } else if (this.props.typeSubmit === "addCatToBook") {
      return (
        <AddCatToBookButton
          showModal={this.showModal}
          buttonRef={(n) => (this.TriggerButton = n)}
        />
      );
    } else if (this.props.typeSubmit === "removeCatFromBook") {
      return (
        <RemoveCatFromBookButton
          showModal={this.showModal}
          buttonRef={(n) => (this.TriggerButton = n)}
        />
      );
    } else if (this.props.typeSubmit === "deleteBook") {
      return (
        <DeleteBookButton
          showModal={this.showModal}
          buttonRef={(n) => (this.TriggerButton = n)}
        />
      );
    }
    // USER
    else if (this.props.typeSubmit === "addRoleToUser") {
      return (
        <AddRoleToUserButton
          showModal={this.showModal}
          buttonRef={(n) => (this.TriggerButton = n)}
        />
      );
    } else if (this.props.typeSubmit === "removeRoleFromUser") {
      return (
        <RemoveRoleFromUserButton
          showModal={this.showModal}
          buttonRef={(n) => (this.TriggerButton = n)}
        />
      );
    } else if (this.props.typeSubmit === "deleteUser") {
      return (
        <DeleteUserButton
          showModal={this.showModal}
          buttonRef={(n) => (this.TriggerButton = n)}
        />
      );
    } else if (this.props.typeSubmit === "blockUser") {
      return (
        <BlockUserSwitch
          showModal={this.showModal}
          buttonRef={(n) => (this.TriggerButton = n)}
          userDetails={this.props.userDetails}
        />
      );
    }
    // ROLE
    else if (this.props.typeSubmit === "addNewRole") {
      return (
        <AddNewRoleButton
          showModal={this.showModal}
          buttonRef={(n) => (this.TriggerButton = n)}
        />
      );
    } else if (this.props.typeSubmit === "updateRole") {
      return (
        <UpdateRoleButton
          showModal={this.showModal}
          buttonRef={(n) => (this.TriggerButton = n)}
        />
      );
    } else if (this.props.typeSubmit === "deleteRole") {
      return (
        <DeleteRoleButton
          showModal={this.showModal}
          buttonRef={(n) => (this.TriggerButton = n)}
        />
      );
    }
  };

  render() {
    return (
      <React.Fragment>
        {this.GetButton()}
        {this.state.isShown ? (
          <Modal
            onSubmit={this.props.onSubmit}
            modalRef={(n) => (this.modal = n)}
            buttonRef={(n) => (this.closeButton = n)}
            closeModal={this.closeModal}
            onKeyDown={this.onKeyDown}
            typeSubmit={this.props.typeSubmit}
            productDetails={this.props.productDetails}
            allCategories={this.props.allCategories}
            userDetails={this.props.userDetails}
            roleDetails={this.props.roleDetails}
            allRoles={this.props.allRoles}
          />
        ) : null}
      </React.Fragment>
    );
  }
}

export default PopupContainer;
