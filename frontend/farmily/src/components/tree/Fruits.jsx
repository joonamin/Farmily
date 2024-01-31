import React from "react";
import EventFruit from "../../assets/images/EventFruit.png";


const Fruits = () => {
  return (
    <div className="bg-blue-200 p-4">
      <h2 className="text-2xl font-bold">열매 메뉴</h2>
      <p>열매 메뉴의 내용을 여기에 추가하세요.</p>
      <img src={EventFruit} alt="과일 이미지" />


    </div>
  );
};

export default Fruits;
