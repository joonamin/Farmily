import { useState, useEffect } from 'react';
import axios from '../api/axios.jsx';
import MemoryItem from '../components/common/MemoryItem.jsx';
import { useSelector, useDispatch } from 'react-redux';
import { setMemory } from '../store/memory.jsx';

import emptyBasket from '../assets/images/emptybasket.png';

export default function MemoryPage() {
  const dispatch = useDispatch();
  const [basket, setBasket] = useState([
    {
      id: 0,
      range: {
        startDate: '',
        endDate: '',
      },
    },
  ]);

  const family = useSelector((state) => state.family.value);

  useEffect(() => {
    axios
      // familyId 수정할것
      .get(`/family/${family.id}/basket`)
      .then((res) => {
        setBasket(res.data);
        dispatch(setMemory(res.data));
      })
      .catch((err) => {
        // console.log(err);
      });
  }, []);

  return (
    <div className="h-full overflow-hidden p-5">
      {basket.length === 0 ? (
        <div className="flex flex-col h-full justify-center items-center">
          <img src={emptyBasket} alt="" className=" size-60 mb-10" />
          <p>가족과의 소중한 순간을 기록해두면, 추억의 보물함이 됩니다.</p>
          <p>
            함께한 순간들을 기억하며 소중한 가족과의 추억을 영원히 간직해보세요
          </p>
        </div>
      ) : (
        <div className="h-full flex flex-wrap justify-center snap-y overflow-y-scroll">
          {/* sprint 정보 보내기 */}
          {basket.map((item, index) => (
            <MemoryItem key={index} {...item} />
          ))}
        </div>
      )}
    </div>
  );
}
