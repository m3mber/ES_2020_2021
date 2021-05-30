import React, { useState, useEffect } from 'react';
import {
  Typography,
  Modal,
  CircularProgress,
  IconButton,
  makeStyles,
} from '@material-ui/core';
import HighlightOffIcon from '@material-ui/icons/HighlightOff';
import SockJsClient from 'react-stomp';

export interface IBusTrackingModalProps {
  isOpen: boolean;
  closeModal: Function;
}

export interface IMessageData {
  id: number;
  node_id: number;
  location_id: number;
  head: string;
  lon: number;
  lat: number;
  speed: number;
  ts: string;
  write_time: string;
}

const useStyles = makeStyles((theme) => ({
  modalStyle: {
    justifyContent: 'center',
    alignItems: 'center',
    display: 'flex',
  },
  modalDiv: {
    overflow: 'auto',
    backgroundColor: 'white',
    width: '30%',
    height: '30%',
    borderRadius: '10px',
    flexDirection: 'column',
    justifyContent: 'start',
    alignItems: 'center',
    padding: 5,
  },
  exitButton: {
    position: 'absolute',
    right: '33%',
    top: '33%',
    backgroundColor: 'white',
    '&:hover': {
      background: 'white',
      transform: 'scale(1.15)',
    },
  },
  loadingLanguages: {
    position: 'absolute',
    width: '30%',
    height: '30%',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: '10px',
  },
}));

const SOCKET_URL = 'http://localhost:8080/real-time/location';

const BusTrackingModal: React.FC<IBusTrackingModalProps> = ({
  isOpen,
  closeModal,
}) => {
  const classes = useStyles();
  const [loading, setLoading] = useState<boolean>(true);
  const [messages, setMessages] = useState<IMessageData[]>([]);

  let onConnected = () => {
    console.log('Connected!!');
  };
  let onMessageReceived = (msg: IMessageData) => {
    console.log('New Message Received!!', msg);
    setMessages(messages.concat(msg));
  };

  return (
    <Modal
      disableBackdropClick={true}
      className={classes.modalStyle}
      open={isOpen}
    >
      <div className={classes.modalDiv}>
        <SockJsClient
          url={SOCKET_URL}
          topics={['/ESP13_bus_data']}
          onConnect={onConnected}
          onDisconnect={console.log('Disconnected!')}
          onMessage={(msg: any) => onMessageReceived(msg)}
          debug={false}
        />
        {messages.length > 0 && (
          <Typography>{messages[0].location_id}</Typography>
        )}
        <IconButton
          className={classes.exitButton}
          onClick={() => {
            closeModal();
          }}
        >
          <HighlightOffIcon fontSize='large' color='secondary' />
        </IconButton>
      </div>
    </Modal>
  );
};

export { BusTrackingModal };
