import { useState } from 'react';
import EventFruit from '../../assets/images/EventFruit.png';

export default function FruitItem({ fruitIndex }) {
  return (
    <>
      <img src={EventFruit} draggable="true" alt="" />
    </>
  );
}
