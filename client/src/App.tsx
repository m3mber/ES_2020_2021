import React, { useState, useEffect } from 'react';
import './App.css';
import { Map } from './Map/Map';
import { HistoricalTable } from 'Table/HistoricalTable';
import { Button } from '@material-ui/core';
import { BusTrackingModal } from 'Modal/BusTrackingModal';

const places = [
  { latitude: 41.1675503, longitude: -8.687209 },
  { latitude: 41.164945, longitude: -8.672024 },
  { latitude: 41.154613, longitude: -8.613325 },
];

const busId = '00000000-0000-0000-0000-000000002481';

function App() {
  // const { isLoaded, loadError } = useLoadScript({
  //   googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY ? ,
  //   libraries,
  // });

  const [isBusTrackingModalOpen, setIsBusTrackingModalOpen] = useState<boolean>(
    false
  );

  const [isBusAlarmModalOpen, setIsBusAlarmModelOpen] = useState<boolean>(
    false
  );
  // const [currentPosition, setCurrentPosition] = useState<any>({});

  // useEffect(() => {
  //   navigator.geolocation.getCurrentPosition(success);
  // }, []);

  // const success = (position: any) => {
  //   const currentPosition = {
  //     lat: position.coords.latitude,
  //     lng: position.coords.longitude,
  //   };
  //   setCurrentPosition(currentPosition);
  // };

  // console.log(currentPosition);

  //-8.610423,41.14931 lat long
  const setBusAlarm = async () => {
    const response = await fetch(
      `http://localhost:8082/current-location?id=${busId}&latLong=-8.610423,41.14931`
    );
    console.log(response);
  };

  return (
    <div className='App' style={{ flexDirection: 'column' }}>
      <Map markers={places} />
      {/*  {center={currentPosition}} */}
      <div
        style={{
          padding: 50,
          width: '90%',
          marginBottom: 40,
        }}
      >
        <HistoricalTable />
      </div>
      <div style={{ position: 'absolute', top: '50%', left: '20' }}>
        <Button
          variant='contained'
          color='primary'
          onClick={() => setIsBusTrackingModalOpen(true)}
        >
          Track bus 2481
        </Button>
      </div>
      <div style={{ position: 'absolute', top: '55%', left: '20' }}>
        <Button
          variant='contained'
          color='primary'
          onClick={() => setBusAlarm()}
        >
          Set alarm for bus 2481
        </Button>
      </div>
      {isBusTrackingModalOpen && (
        <BusTrackingModal
          isOpen={isBusTrackingModalOpen}
          busId={busId}
          closeModal={() => {
            setIsBusTrackingModalOpen(false);
          }}
        />
      )}
    </div>
  );
}

export default App;
