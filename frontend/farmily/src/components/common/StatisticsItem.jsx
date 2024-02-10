import { useState, useEffect } from 'react';
import fruitImages from '../../api/fruitImages';
import HoverBox from './HoverBox';

export default function StatisticsItem({ type, count, text }) {
  const [isHover, setIsHover] = useState(false);

  const openHover = () => {
    setIsHover(true);
  };

  const closeHover = () => {
    setIsHover(false);
  };

  return (
    <>
      <span className="flex my-auto mr-5 ">
        <img
          className="h-8 mr-3"
          src={fruitImages[type]}
          alt=""
          onMouseEnter={openHover}
          onMouseLeave={closeHover}
        />{' '}
        {isHover ? (
          <HoverBox title={text} />
        ) : (
          <span className="flex">
            <p className="my-auto text-xl mr-1">X</p>
            <p className="my-auto text-xl ">{count}</p>
          </span>
        )}
      </span>
    </>
  );
}
