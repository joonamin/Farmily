export default function ImageItem(image) {
  return (
    <div className="h-100 m-1 border-4 border-black rounded-lg flex-shrink-0 snap-center">
      <img className="w-full h-full rounded-lg object-cover" src={image.image} alt="" />
    </div>
  )
}