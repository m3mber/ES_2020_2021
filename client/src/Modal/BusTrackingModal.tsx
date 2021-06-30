import React, { useState, useEffect } from 'react';
import { Typography, Modal, IconButton, makeStyles } from '@material-ui/core';
import HighlightOffIcon from '@material-ui/icons/HighlightOff';
import { Map } from '../Map/Map';

export interface IBusTrackingModalProps {
  isOpen: boolean;
  closeModal: Function;
  busId: string;
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
    width: '80%',
    height: '80%',
    borderRadius: '10px',
    flexDirection: 'column',
    justifyContent: 'start',
    alignItems: 'center',
    padding: 5,
  },
  exitButton: {
    position: 'absolute',
    right: '7%',
    top: '7%',
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

const BusTrackingModal: React.FC<IBusTrackingModalProps> = ({
  isOpen,
  closeModal,
  busId,
}) => {
  const classes = useStyles();

  let eventSource: EventSource | undefined = undefined;
  const [listening, setListening] = useState(false);
  const [places, setPlaces] = useState<
    { latitude: number; longitude: number }[]
  >([]);
  const [routes, setRoutes] = useState<
    { latitude: number; longitude: number }[]
  >([]);

  useEffect(() => {
    fetch(`http://localhost:13004/bus/routes?busId=${busId}`)
      .then((res) => res.json())
      .then((result) => {
        for (let i = 0; i < result.length; i = i + 2) {
          setRoutes((old) => [
            ...old,
            {
              latitude: +result[i],
              longitude: +result[i + 1],
            },
          ]);
        }
      });

    getBusStops();
    if (!listening) {
      eventSource = new EventSource(
        `http://localhost:13001/location?id=${busId}`
      );

      eventSource.onopen = (event) => {
        console.log('connection opened');
      };

      eventSource.onmessage = (event) => {
        console.log('result', event.data);
        let message = event.data;
        let messageArray = message.split(','); //on index 0 lat, on index 1 long

        setPlaces((old) => [
          ...old,
          {
            latitude: +messageArray[0],
            longitude: +messageArray[1],
          },
        ]);
      };

      eventSource.onerror = (event: any) => {
        if (eventSource && event.target) {
          if (event.target.readyState === EventSource.CLOSED) {
            console.log('eventsource closed (' + event.target.readyState + ')');
          }
          eventSource.close();
        }
      };

      setListening(true);
    }
    return () => {
      eventSource && eventSource.close();
      console.log('eventsource closed');
    };
  }, []);

  const getBusStops = async () => {
    const databaseResponse = await fetch(
      `http://localhost:13004/bus/locations?busId=${busId}`
    );
    const body = await databaseResponse.json();
    for (let i = 0; i < body.length; i = i + 2) {
      setPlaces((old) => [
        ...old,
        { latitude: parseFloat(body[i]), longitude: parseFloat(body[i + 1]) },
      ]);
    }
  };

  return (
    <Modal
      disableBackdropClick={true}
      className={classes.modalStyle}
      open={isOpen}
    >
      <div className={classes.modalDiv}>
        <Map markers={routes} currentPosition={places[places.length - 1]} />
        {/* {data.length > 0 &&
          data.map((item: any, index: number) => (
            <Typography key={index}>{item}</Typography>
          ))} */}
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
