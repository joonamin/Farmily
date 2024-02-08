import { useState } from 'react';
import fruitImages from '../../api/fruitImages.jsx';
import { useSelector } from 'react-redux';

export default function FruitItem({ type }) {
  const family = useSelector((state) => state.family.value);
  const [fruitImg, setFruitImg] = useState();

  if (type === 'DAILY') {
    setFruitImg(fruitImages[family.fruitSkins.daily]);
  } else if (type === 'EVENT') {
    setFruitImg(fruitImages[family.fruitSkins.event]);
  } else if (type === 'CHALLENGE') {
    setFruitImg(fruitImages[family.fruitSkins.challenge]);
  }

  return (
    <>
      <img src={fruitImg} draggable="true" alt="" />
    </>
  );
}
