import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import axios from '../../api/axios.jsx';

// 이미지 이름을 itemCode에 따라 수정
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
import ALPHABET_A_B from '../../assets/images/fruits/1_b.png';
import ALPHABET_B_B from '../../assets/images/fruits/2_b.png';
import ALPHABET_C_B from '../../assets/images/fruits/3_b.png';
import ALPHABET_D_B from '../../assets/images/fruits/4_b.png';
import ALPHABET_E_B from '../../assets/images/fruits/5_b.png';
import ALPHABET_F_B from '../../assets/images/fruits/6_b.png';
import ALPHABET_G_B from '../../assets/images/fruits/7_b.png';
import ALPHABET_H_B from '../../assets/images/fruits/8_b.png';
import ALPHABET_I_B from '../../assets/images/fruits/9_b.png';
import ALPHABET_J_B from '../../assets/images/fruits/10_b.png';
import ALPHABET_K_B from '../../assets/images/fruits/11_b.png';
import ALPHABET_L_B from '../../assets/images/fruits/12_b.png';
import ALPHABET_N_B from '../../assets/images/fruits/13_b.png';
import ALPHABET_M_B from '../../assets/images/fruits/14_b.png';
import ALPHABET_O_B from '../../assets/images/fruits/15_b.png';
import ALPHABET_P_B from '../../assets/images/fruits/16_b.png';
import ALPHABET_Q_B from '../../assets/images/fruits/17_b.png';
import ALPHABET_R_B from '../../assets/images/fruits/18_b.png';
import ALPHABET_S_B from '../../assets/images/fruits/19_b.png';
import ALPHABET_T_B from '../../assets/images/fruits/20_b.png';
import ALPHABET_U_B from '../../assets/images/fruits/21_b.png';

const Collection = () => {
  const family = useSelector((state) => state.family.value);
  const [fruitInventory, setFruitInventory] = useState([]);
  const familyId = family.id;
  const sprintId = family.mainSprint.sprintId;
  useEffect(() => {
    axios
      .get(`family/${familyId}/inventory/${sprintId}`)
      .then((response) => {
        const familyItemList = response.data.familyItemList;

        if (Array.isArray(familyItemList)) {
          setFruitInventory(familyItemList); // familyItemList가 배열이면 상태 업데이트
        } else {
          console.error('familyItemList is not an array:', familyItemList);
          setFruitInventory([]); // familyItemList가 배열이 아니면 빈 배열로 초기화
        }
      })
      .catch((error) => {
        console.error('Error fetching fruit inventory:', error);
      });
  }, [familyId, sprintId]);

  const fruitsToShow = [
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
  ];

  const fruitsToShowB = [
    ALPHABET_A_B,
    ALPHABET_B_B,
    ALPHABET_C_B,
    ALPHABET_D_B,
    ALPHABET_E_B,
    ALPHABET_F_B,
    ALPHABET_G_B,
    ALPHABET_H_B,
    ALPHABET_I_B,
    ALPHABET_J_B,
    ALPHABET_K_B,
    ALPHABET_L_B,
    ALPHABET_N_B,
    ALPHABET_M_B,
    ALPHABET_O_B,
    ALPHABET_P_B,
    ALPHABET_Q_B,
    ALPHABET_R_B,
    ALPHABET_S_B,
    ALPHABET_T_B,
    ALPHABET_U_B,
  ];

  const sortedFruits = fruitsToShow
    .map((fruit, index) => {
      const itemCode = `ALPHABET_${String.fromCharCode(65 + index)}`;
      const isOwned = fruitInventory.some((item) => item.itemCode === itemCode);
      return {
        fruit: isOwned ? fruit : fruitsToShowB[index],
        isOwned,
        itemCode,
      };
    })
    .sort((a, b) => b.isOwned - a.isOwned); // 소유된 과일을 배열의 앞쪽으로

  const imageSize = '100px';

  return (
    <div>
      <h2
        style={{
          textAlign: 'center',
          position: 'sticky',
          top: 0,
          backgroundColor: 'white',
          fontSize: 'x-large',
        }}
      >
        열매 도감
      </h2>
      <div
        style={{
          display: 'flex',
          flexWrap: 'wrap',
          justifyContent: 'flex-start',
          alignItems: 'flex-start',
        }}
      >
        {sortedFruits.map(({ fruit, itemCode }) => (
          <div
            key={itemCode}
            style={{ width: imageSize, height: imageSize, margin: '5px' }}
          >
            <img
              src={fruit}
              alt={itemCode}
              style={{ width: '100%', height: '100%' }}
            />
          </div>
        ))}
      </div>
    </div>
  );
};

export default Collection;
