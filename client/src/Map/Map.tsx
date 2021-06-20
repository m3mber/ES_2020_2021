/* eslint-disable no-undef */
import React, { useState, useEffect } from 'react';
import DirectionsBusIcon from '@material-ui/icons/DirectionsBus';
import BusIcon from 'assets/Aiga_bus.svg';

const {
  compose,
  withProps,
  withStateHandlers,
  lifecycle,
} = require('recompose');
const {
  withScriptjs,
  withGoogleMap,
  GoogleMap,
  Marker,
  DirectionsRenderer,
} = require('react-google-maps');

const { InfoBox } = require('react-google-maps/lib/components/addons/InfoBox');

function MapDirectionsRenderer(props: { places: any; travelMode: any }) {
  const [directions, setDirections] = useState(null);
  const [error, setError] = useState<any>(null);

  useEffect(() => {
    const { places, travelMode } = props;
    const waypoints: any[] = [];
    places.forEach((p: any) => {
      waypoints.push({
        location: { lat: p.latitude, lng: p.longitude },
        stopover: true,
      });
    });

    const origin = waypoints.shift().location;
    const destination = waypoints.pop().location;

    const directionsService = new google.maps.DirectionsService();

    directionsService.route(
      {
        origin: origin,
        destination: destination,
        travelMode: travelMode,
        waypoints: waypoints,
      },
      (result: any, status) => {
        if (status === google.maps.DirectionsStatus.OK) {
          setDirections(result);
        } else {
          setError(result);
        }
      }
    );
  });

  if (error) {
    return <h1>{error?.status}</h1>;
  }
  return directions && <DirectionsRenderer directions={directions} />;
}

const Map = compose(
  withProps({
    googleMapURL: `https://maps.googleapis.com/maps/api/js?key=${process.env.REACT_APP_GOOGLE_MAPS_API_KEY}&v=3.exp&libraries=geometry,drawing,places`,
    loadingElement: <div style={{ height: `100%` }} />,
    containerElement: <div style={{ height: `100%` }} />,
    mapElement: <div style={{ height: `100%` }} />,
    center: { lat: 41.14961, lng: -8.61099 },
  }),
  withStateHandlers(
    () => ({
      isOpen: false,
    }),
    {
      onToggleOpen: (isOpen: any) => () => ({
        isOpen: !isOpen,
      }),
    }
  ),
  withScriptjs,
  withGoogleMap
)(
  (props: {
    center: { lat: number; lng: number };
    onToggleOpen: any;
    isOpen: any;
    markers: any;
  }) =>
    props.center.lat && (
      <GoogleMap defaultZoom={12} defaultCenter={props.center} props>
        {props.markers.length > 0 && (
          <Marker
            icon={{
              url: BusIcon,
              scaledSize: new window.google.maps.Size(20, 20),
            }}
            position={{
              lat: props.markers[props.markers.length - 1]?.latitude,
              lng: props.markers[props.markers.length - 1]?.longitude,
            }}
          />
        )}
        {props.markers.length > 1 && (
          <MapDirectionsRenderer
            places={props.markers}
            travelMode={google.maps.TravelMode.DRIVING}
          />
        )}
      </GoogleMap>
    )
);

export { Map };
