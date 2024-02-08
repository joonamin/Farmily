import { useState, useEffect } from 'react';
import maintree from '../../assets/images/maintree.png';
import FruitItem from './FruitItem.jsx';
import MottoBoard from '../../assets/images/motto.png';

import { useSelector } from 'react-redux';

export default function MainTree({ treeFruits }) {
  // 임시데이터, 연결 후 열매에 대한 데이터 받아오기

  const family = useSelector((state) => state.family.value);

  const [isMotto, setIsMotto] = useState(false);

  const onClickHandler = () => {
    setIsMotto(!isMotto);
  };

  return (
    <div className="relative overflow-hidden">
      <img className="" src={maintree} alt="MainTree" />
      {treeFruits.map((fruit, index) => (
        <div
          key={index}
          className="absolute w-10 h-10"
          style={{
            top: fruit.position.col,
            left: fruit.position.row,
          }}
        >
          <FruitItem />
        </div>
      ))}

      <div className="absolute inset-x-0 bottom-32 flex justify-center">
        <div
          className="h-32 opacity-100 text-opacity-100 w-16 hover:cursor-pointer"
          onClick={onClickHandler}
        ></div>
      </div>
      {
        <div className="flex h-28 items-center justify-center">
          {isMotto && (
            <div
              className={`relative animate-fade-up h-full flex items-center`}
            >
              <img src={MottoBoard} />
              <p className="absolute text-xl top-0 text-black text h-full inset-x-10 flex justify-center items-center">
                {family.motto}
              </p>
            </div>
          )}
        </div>
      }
    </div>
  );
}
