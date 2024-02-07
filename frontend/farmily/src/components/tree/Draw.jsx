import React, { useState, useEffect } from 'react';
import Box from '../../assets/images/Box.png';
import Raffling from '../../assets/images/Box_raffling.gif';
import ALPHABET_A from '../../assets/images/fruits/1.png';
import ALPHABET_B from '../../assets/images/fruits/2.png';
import ALPHABET_C from '../../assets/images/fruits/3.png';
import ALPHABET_D from '../../assets/images/fruits/4.png';
import ALPHABET_E from '../../assets/images/fruits/5.png';
import ALPHABET_F from '../../assets/images/fruits/6.png';
import ALPHABET_G from '../../assets/images/fruits/7.png';
import ALPHABET_H from '../../assets/images/fruits/8.png';
import ALPHABET_I from '../../assets/images/fruits/9.png';
import ALPHABET_J from '../../assets/images/fruits/10.png';
import ALPHABET_K from '../../assets/images/fruits/11.png';
import ALPHABET_L from '../../assets/images/fruits/12.png';
import ALPHABET_M from '../../assets/images/fruits/13.png';
import ALPHABET_N from '../../assets/images/fruits/14.png';
import ALPHABET_O from '../../assets/images/fruits/15.png';
import ALPHABET_P from '../../assets/images/fruits/16.png';
import ALPHABET_Q from '../../assets/images/fruits/17.png';
import ALPHABET_R from '../../assets/images/fruits/18.png';
import ALPHABET_S from '../../assets/images/fruits/19.png';
import ALPHABET_T from '../../assets/images/fruits/20.png';
import ALPHABET_U from '../../assets/images/fruits/21.png';
import { useSelector } from 'react-redux';

const fruitImages = {
  ALPHABET_A,
  ALPHABET_B,
  ALPHABET_C,
  ALPHABET_D,
  ALPHABET_E,
  ALPHABET_F,
  ALPHABET_G,
  ALPHABET_H,
  ALPHABET_I,
  ALPHABET_J,
  ALPHABET_K,
  ALPHABET_L,
  ALPHABET_N,
  ALPHABET_M,
  ALPHABET_O,
  ALPHABET_P,
  ALPHABET_Q,
  ALPHABET_R,
  ALPHABET_S,
  ALPHABET_T,
  ALPHABET_U,
};

const Draw = ({ isRaffling, rafflingResponse, familyPoint }) => {
  const family = useSelector((state) => state.family.value);
  const [imageSrc, setImageSrc] = useState(Box);
  const [fruitImageSrc, setFruitImageSrc] = useState('');
  const [showRafflingImage, setShowRafflingImage] = useState(true);
  const [timers, setTimers] = useState({
    rafflingHide: null,
    fruitShow: null,
    fruitHide: null,
    boxShow: null,
  });

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
      clearTimeout(timerRafflingHide);
      clearTimeout(timerFruitShow);
      clearTimeout(timerFruitHide);
      clearTimeout(timerBoxShow);
    };
  }, [isRaffling, rafflingResponse]);

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
       <h2>보유 포인트: <span style={pointStyle}>{familyPoint}</span>/100</h2>
      <p className="fontSize-small">뽑기 시 100포인트 차감됩니다.</p>
      <br />
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100%' }}>
        <img src={imageSrc} alt="" style={{ visibility: imageSrc ? 'visible' : 'hidden' }} className="h-full" />
        {fruitImageSrc && (
          <img
            src={fruitImageSrc}
            alt="Fruit"
            style={fruitImageStyle}
          />
        )}
      </div>
    </div>
  );
};

export default Draw;