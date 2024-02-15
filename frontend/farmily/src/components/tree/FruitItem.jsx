import { useState } from 'react';
import { useSelector } from 'react-redux';
import fruitImages from '../../api/fruitImages.jsx';
import HoverBox from '../common/HoverBox.jsx';

export default function FruitItem({ type, title, id }) {
  const family = useSelector((state) => state.family.value);
  const [fruitImg, setFruitImg] = useState();
  const [isHover, setIsHover] = useState(false);

  const openHover = () => {
    setIsHover(true);
  };

  const closeHover = () => {
    setIsHover(false);
  };

  if (type === 'DAILY') {
    setFruitImg(fruitImages[family.fruitSkins.daily]);
  } else if (type === 'EVENT') {
    setFruitImg(fruitImages[family.fruitSkins.event]);
  } else if (type === 'CHALLENGE') {
    setFruitImg(fruitImages[family.fruitSkins.challenge]);
  }

  return (
    <div className="flex items-center hover:cursor-pointer z-50">
      <img
        onMouseEnter={openHover}
        onMouseLeave={closeHover}
        src={fruitImg}
        draggable="true"
        alt=""
      />
      {isHover && <HoverBox title={title} />}
    </div>
  );
}
