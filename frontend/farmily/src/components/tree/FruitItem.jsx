import { useState } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import fruitImages from '../../api/fruitImages.jsx';
import HoverBox from '../common/HoverBox.jsx';

export default function FruitItem({ type, title, id }) {
  const navigate = useNavigate();
  const family = useSelector((state) => state.family.value);
  const [fruitImg, setFruitImg] = useState();
  const [isHover, setIsHover] = useState(false);

  const openHover = () => {
    setIsHover(true);
  };

  const closeHover = () => {
    setIsHover(false);
  };

  const onClickHandler = () => {
    if (
      location.pathname.includes('tree') &&
      !location.pathname.includes('decorate')
    ) {
      // 포함되어 있을 경우 원하는 동작 수행
      navigate(`/family/record/${type.toLowerCase()}/${id}`);
    }
  };

  if (type === 'DAILY') {
    setFruitImg(fruitImages[family.fruitSkins.daily]);
  } else if (type === 'EVENT') {
    setFruitImg(fruitImages[family.fruitSkins.event]);
  } else if (type === 'CHALLENGE') {
    setFruitImg(fruitImages[family.fruitSkins.challenge]);
  }

  return (
    <div className="flex items-center">
      <img
        onMouseEnter={openHover}
        onMouseLeave={closeHover}
        onClick={onClickHandler}
        src={fruitImg}
        draggable="true"
        alt=""
      />
      {isHover && <HoverBox title={title} />}
    </div>
  );
}
