import React from 'react';
import './App.css';
import { Map } from './Map/Map';
import { HistoricalTable } from 'Table/HistoricalTable';

const places = [
  { latitude: 41.1675503, longitude: -8.687209 },
  { latitude: 41.164945, longitude: -8.672024 },
  { latitude: 41.154613, longitude: -8.613325 },
];

function App() {
  // const { isLoaded, loadError } = useLoadScript({
  //   googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY ? ,
  //   libraries,
  // });

  return (
    <div className='App'>
      <Map markers={places} />
      <div
        style={{
          padding: 50,
          width: '90%',
          marginBottom: 40,
        }}
      >
        <HistoricalTable />
      </div>
    </div>
  );
}

export default App;
