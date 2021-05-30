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

const busId = '00000000-0000-0000-0000-000000002518';

function App() {
  // const { isLoaded, loadError } = useLoadScript({
  //   googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY ? ,
  //   libraries,
  // });

  const [isBusTrackingModalOpen, setIsBusTrackingModalOpen] = useState<boolean>(
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

  return (
    <div className='App'>
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
      <Button onClick={() => setIsBusTrackingModalOpen(true)}>
        Track bus 2518
      </Button>
      {isBusTrackingModalOpen && (
        <BusTrackingModal
          isOpen={isBusTrackingModalOpen}
          closeModal={() => {}}
        />
      )}
    </div>
  );
}

export default App;
