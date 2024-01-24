import React, { Component } from "react";
import "./ToolbarComponent.css";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Mic from "@mui/icons-material/Mic";
import MicOff from "@mui/icons-material/MicOff";
import Videocam from "@mui/icons-material/Videocam";
import VideocamOff from "@mui/icons-material/VideocamOff";
import IconButton from "@mui/material/IconButton";
import Tooltip from "@mui/material/Tooltip";
import QuestionAnswer from "@mui/icons-material/QuestionAnswer";

export default class ToolbarComponent extends Component {
  constructor(props) {
    super(props);
    this.state = { showChat: false };
  }

  micStatusChanged = () => {
    this.props.micStatusChanged();
  };

  camStatusChanged = () => {
    this.props.camStatusChanged();
  };

  toggleChat = () => {
    this.setState({ showChat: !this.state.showChat });
    this.props.toggleChat();
  };

  render() {
    const mySessionId = this.props.sessionId;
    const localUser = this.props.user;

    return (
      <AppBar className="toolbar" id="header">
        <Toolbar className="toolbar">
          <div className="buttonsContent">
            <IconButton
              color="inherit"
              className="navButton"
              id="navMicButton"
              onClick={this.micStatusChanged}
            >
              {localUser !== undefined && localUser.isAudioActive() ? (
                <Mic />
              ) : (
                <MicOff color="secondary" />
              )}
            </IconButton>

            <IconButton
              color="inherit"
              className="navButton"
              id="navCamButton"
              onClick={this.camStatusChanged}
            >
              {localUser !== undefined && localUser.isVideoActive() ? (
                <Videocam />
              ) : (
                <VideocamOff color="secondary" />
              )}
            </IconButton>

            <IconButton
              color="inherit"
              onClick={this.toggleChat}
              id="navChatButton"
            >
              {this.props.showNotification && <div id="point" className="" />}
              <Tooltip title="Chat">
                <QuestionAnswer />
              </Tooltip>
            </IconButton>
          </div>
        </Toolbar>
      </AppBar>
    );
  }
}
