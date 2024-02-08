import { useState, useEffect } from 'react';
import axios from '../api/axios.jsx';
import MemoryItem from '../components/common/MemoryItem.jsx';
import { useSelector, useDispatch } from 'react-redux';
import { setMemory } from '../store/memory.jsx';

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
      <h1>추억보기 페이지</h1>
      <div className="h-full flex flex-wrap justify-center snap-y overflow-y-scroll">
        {/* sprint 정보 보내기 */}
        {basket.map((item, index) => (
          <MemoryItem key={index} {...item} />
        ))}
      </div>
    </div>
  );
}
