import chunsik from '../assets/images/chunsik.jpg';
import ImageItem from './ImageItem.jsx';

// 테스트용 데이터 TESTITEMS, chunsik 데이터 받아오면 삭제
const TESTITEMS = [
  { 'image' : chunsik }, 
  { 'image' : chunsik }, 
  { 'image' : chunsik }, 
  { 'image' : chunsik }, 
  { 'image' : chunsik }, 
  { 'image' : chunsik }, 
  { 'image' : chunsik }, 
  { 'image' : chunsik }, 
  { 'image' : chunsik }, 
]

export default function ImageList() {
  return (

    <div className=" h-2/5 p-5 snap-x overflow-x-scroll flex">
      {TESTITEMS.map((item, index) => (
        <ImageItem key={index} image={item.image} />
        ))}
    </div>
  )
}