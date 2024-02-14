import { Link } from 'react-router-dom';
import fruits from '../../assets/images/fruits.png';

export default function MemoryItem(memory) {
  // 시작날짜 월 뽑아오는 방법
  const date = memory.range.startDate;
  const dateObject = new Date(date);
  const month = dateObject.getMonth() + 1;
  const year = dateObject.getFullYear();
  // 근데 이게 임의로 스프린트 종료 시킨 데이터라서 1월만 들어가있음

  return (
    // 데이터 받아온 후 sprint에 맞는
    <Link to={`/family/record/${memory.id}`}>
      <div className="p-5 snap-center">
        <img src={fruits} alt="" />
        <p>
          {year}년 {month}월 <br />
          수확한 과일바구니
        </p>
      </div>
    </Link>
  );
}
