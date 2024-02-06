import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import axios from "../../api/axios.jsx"

import fruit1 from '../../assets/images/fruits/1.png';
import fruit2 from '../../assets/images/fruits/2.png';
import fruit3 from '../../assets/images/fruits/3.png';
import fruit4 from '../../assets/images/fruits/4.png';
import fruit5 from '../../assets/images/fruits/5.png';
import fruit6 from '../../assets/images/fruits/6.png';
import fruit7 from '../../assets/images/fruits/7.png';
import fruit8 from '../../assets/images/fruits/8.png';
import fruit9 from '../../assets/images/fruits/9.png';
import fruit10 from '../../assets/images/fruits/10.png';
import fruit11 from '../../assets/images/fruits/11.png';
import fruit12 from '../../assets/images/fruits/12.png';
import fruit13 from '../../assets/images/fruits/13.png';
import fruit14 from '../../assets/images/fruits/14.png';
import fruit15 from '../../assets/images/fruits/15.png';
import fruit16 from '../../assets/images/fruits/16.png';
import fruit17 from '../../assets/images/fruits/17.png';
import fruit18 from '../../assets/images/fruits/18.png';
import fruit19 from '../../assets/images/fruits/19.png';
import fruit20 from '../../assets/images/fruits/20.png';
import fruit21 from '../../assets/images/fruits/21.png';
import fruit1B from '../../assets/images/fruits/1_b.png';
import fruit2B from '../../assets/images/fruits/2_b.png';
import fruit3B from '../../assets/images/fruits/3_b.png';
import fruit4B from '../../assets/images/fruits/4_b.png';
import fruit5B from '../../assets/images/fruits/5_b.png';
import fruit6B from '../../assets/images/fruits/6_b.png';
import fruit7B from '../../assets/images/fruits/7_b.png';
import fruit8B from '../../assets/images/fruits/8_b.png';
import fruit9B from '../../assets/images/fruits/9_b.png';
import fruit10B from '../../assets/images/fruits/10_b.png';
import fruit11B from '../../assets/images/fruits/11_b.png';
import fruit12B from '../../assets/images/fruits/12_b.png';
import fruit13B from '../../assets/images/fruits/13_b.png';
import fruit14B from '../../assets/images/fruits/14_b.png';
import fruit15B from '../../assets/images/fruits/15_b.png';
import fruit16B from '../../assets/images/fruits/16_b.png';
import fruit17B from '../../assets/images/fruits/17_b.png';
import fruit18B from '../../assets/images/fruits/18_b.png';
import fruit19B from '../../assets/images/fruits/19_b.png';
import fruit20B from '../../assets/images/fruits/20_b.png';
import fruit21B from '../../assets/images/fruits/21_b.png';


const Collection = () => {
  const familyId = useSelector((state) => state.family.value.id);
  const [fruitInventory, setFruitInventory] = useState([]);

  useEffect(() => {
    // 가족의 과일 보유 정보를 가져오기
    axios.get(`family/${familyId}/inventory`)
      .then((response) => {
        // 과일 보유 정보를 state에 저장
        setFruitInventory(response.data);
      })
      .catch((error) => {
        console.error("Error fetching fruit inventory:", error);
      });
  }, [familyId]);

  const fruitsToShow = [
    fruit1,
    fruit2,
    fruit3,
    fruit4,
    fruit5,
    fruit6,
    fruit7,
    fruit8,
    fruit9,
    fruit10,
    fruit11,
    fruit12,
    fruit13,
    fruit14,
    fruit15,
    fruit16,
    fruit17,
    fruit18,
    fruit19,
    fruit20,
    fruit21,
  ];

  const fruitsToShowB = [
    fruit1B,
    fruit2B,
    fruit3B,
    fruit4B,
    fruit5B,
    fruit6B,
    fruit7B,
    fruit8B,
    fruit9B,
    fruit10B,
    fruit11B,
    fruit12B,
    fruit13B,
    fruit14B,
    fruit15B,
    fruit16B,
    fruit17B,
    fruit18B,
    fruit19B,
    fruit20B,
    fruit21B,
  ];

  const imageSize = "100px"; // 이미지 크기 설정 (원하는 크기로 변경)

  return (
    <div>
      <h2 style={{ textAlign: "center", position: "sticky", top: 0, backgroundColor: "white", fontSize:"x-large" }}>열매 도감</h2>
      <div style={{ display: "flex", flexWrap: "wrap", justifyContent: "space-around" }}>
        {fruitsToShow.map((fruit, index) => {
          const isOwned = fruitInventory.includes(`fruit${index + 1}`);
          const displayedFruit = isOwned ? fruit : fruitsToShowB[index];

          return (
            <div key={fruit} style={{ width: imageSize, height: imageSize }}>
              <img
                src={displayedFruit}
                alt={`fruit${index + 1}`}
                style={{ width: "100%", height: "100%" }} // 이미지 크기 스타일 설정
              />
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Collection;