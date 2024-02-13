import React, { useState, useEffect } from 'react';
import Box from '../../assets/images/Box.png';
import Raffling from '../../assets/images/Box_raffling.gif';
import fruitImages from '../../api/fruitImages.jsx';
import { useSelector } from 'react-redux';

const Draw = ({ isRaffling, rafflingResponse, familyPoint, resetTrigger }) => {
  const family = useSelector((state) => state.family.value);
  const [imageSrc, setImageSrc] = useState(Box);
  const [fruitImageSrc, setFruitImageSrc] = useState('');
  const [showRafflingImage, setShowRafflingImage] = useState(true);

  useEffect(() => {
    setImageSrc(Box);
    let timerRafflingHide, timerFruitShow, timerFruitHide, timerBoxShow;

    if (isRaffling) {
      setImageSrc(Raffling); // 뽑기 시작 시 Raffling 이미지로 변경
      setShowRafflingImage(true);

      // 3.6초 후 fruit 이미지 정 중앙에 표시
      timerFruitShow = setTimeout(() => {
        if (rafflingResponse) {
          const fruitKey = rafflingResponse.rafflingCode;
          const selectedFruitImage = fruitImages[fruitKey];
          setFruitImageSrc(selectedFruitImage);
        }
      }, 3600);

      // 4.5초 후 Raffling 이미지 사라짐
      timerRafflingHide = setTimeout(() => {
        setImageSrc('');
        setShowRafflingImage(false); // Raffling 이미지 숨김
      }, 4500);

      // 5.9초 후 fruit 이미지 사라짐
      timerFruitHide = setTimeout(() => {
        setFruitImageSrc('');
      }, 6900);

      // 6초 후 Box 이미지로 복귀
      timerBoxShow = setTimeout(() => {
        setImageSrc(Box);
      }, 7000);
    } else {
      // isRaffling이 false이면 이미지 초기화
      setImageSrc(Box);
      setShowRafflingImage(false);
      setFruitImageSrc('');
    }

    return () => {
      clearTimeout(timerFruitShow);
      clearTimeout(timerRafflingHide);
      clearTimeout(timerFruitHide);
      clearTimeout(timerBoxShow);
    };
  }, [isRaffling, rafflingResponse, resetTrigger]);

  const pointStyle = familyPoint < 100 ? { color: 'red' } : { color: 'green' };
  const fruitMargin = showRafflingImage ? '0' : '80px'; // Raffling
  const fruitImageStyle = {
    position: 'absolute',
    zIndex: 2,
    width: showRafflingImage ? `100px` : `200px`,
    height: showRafflingImage ? `100px` : `200px`,
    visibility: fruitImageSrc ? 'visible' : 'hidden',
    marginTop: showRafflingImage ? '0' : '230px', // fruit 이미지에 마진 적용
  };

  return (
    <div>
      <h2>
        보유 포인트: <span style={pointStyle}>{familyPoint}</span>/100
      </h2>
      <p className="fontSize-small">뽑기 시 100포인트 차감됩니다.</p>
      <br />
      <div
        style={{
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          height: '100%',
        }}
      >
        <img
          src={imageSrc}
          alt=""
          style={{ visibility: imageSrc ? 'visible' : 'hidden' }}
          className="h-full"
        />
        {fruitImageSrc && (
          <img src={fruitImageSrc} alt="Fruit" style={fruitImageStyle} />
        )}
      </div>
    </div>
  );
};

export default Draw;
