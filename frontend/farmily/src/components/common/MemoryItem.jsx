import fruits from '../../assets/images/fruits.png';

export default function MemoryItem() {
  return (
    // 데이터 받아온 후 sprint에 맞는
    // memory 게시판으로 보내는 Link 추가
    <div className="p-5 snap-center">
      <img src={fruits} alt="" />
      <p>
        2024년 N월 <br />
        수확한 과일바구니
      </p>
    </div>
  );
}
